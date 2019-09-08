import React, { Component } from "react";
import { Bar } from "react-chartjs-2";
import axios from "axios";
import axiosCancel from "axios-cancel";
import { connect } from "react-redux";
import { setData, selectCandidateIndex } from "../../reducer/actions";

import Loading from "../../Loading";

import "./RankGraph.scss";
import routeConfig from "../../routeConfig";

axiosCancel(axios, {
  debug: false // default
});

const styles = {
  fontFamily: "sans-serif",
  textAlign: "center"
};
const options = {
  scales: {
    xAxes: [
      {
        gridLines: {
          display: false
        }
      }
    ],
    yAxes: [
      {
        gridLines: {
          display: false
        },
        ticks: {
          beginAtZero: true
        }
      }
    ]
  }
};

let _self;

class RankGraph extends Component {
  constructor(props) {
    super(props);
    this.requestId = 0;
    this.state = {};
    _self = this;
  }

  componentDidMount() {
    // const { ids } = this.props;
    this.initNewReq();
    this.intervalId = window.setInterval(() => {
      _self.cancelPrevReq();
      _self.initNewReq();
    }, 60000);
  }

  cancelPrevReq() {
    if (this.requestId) {
      axios.cancel(this.currentRequestId);
    }
  }

  initNewReq() {
    const { ids } = this.props;
    console.log("ids", ids);
    this.requestId++;
    if (this.requestId > 5) {
      window.clearInterval(_self.intervalId);
      return 0;
    }
    this.currentRequestId = `requestNo${this.requestId}`;
    const promise = axios
      .get(
        `http://localhost:8080/codeQuality/result/${ids}?curentTime=${new Date()}`,
        {
          requestId: _self.currentRequestId
        }
      )
      .then(res => {
        // window.clearInterval(_self.intervalId);
        const {
          data: { data }
        } = res;
        this.props.setData(data);
      });
  }

  getGraphData = response => {
    let tempName = [];
    let tempScore = [];
    let graphConfig = {
      labels: [],
      datasets: [
        {
          label: "rank",
          backgroundColor: "rgba(75,192,192,0.4)",
          borderColor: "rgba(75,192,192,1)",
          hoverBackgroundColor: "rgba(75,192,19,1)",
          hoverBorderColor: "red",
          hoverBorderWidth: 2,
          data: []
        }
      ]
    };
    response.map(obj => {
      tempName.push(obj.userName);
      tempScore.push(obj.score);
      return "";
    });
    graphConfig.labels = tempName;
    graphConfig.datasets[0].data = tempScore;
    return graphConfig;
  };

  onclickGraph = clickedGraph => {
    const { data, selectCandidateIndex } = this.props;
    const { history } = this.props;
    if (clickedGraph[0] === undefined) {
      return;
    }
    console.log("clickedGraph", data[clickedGraph[0]._index]);
    selectCandidateIndex(clickedGraph[0]._index);
    history.push(routeConfig.summary, {
      type: "RankGraph",
      index: clickedGraph[0]._index
    });
  };
  render() {
    const { data: response } = this.props;
    console.log("data", response);
    return (
      <div>
        {response == null || response.length === 0 ? (
          <Loading />
        ) : (
          <div style={styles}>
            <table style={{ width: "800px" }}>
              <tbody>
                <div className="RankGraph">
                  <h2>Candidate Ranking Graph</h2>
                  <Bar
                    ref="chart"
                    data={this.getGraphData(response)}
                    onElementsClick={this.onclickGraph}
                    options={options}
                  />
                </div>
              </tbody>
            </table>
          </div>
        )}
      </div>
    );
  }
}

const mapStateToProps = ({ appDataReducer }) => {
  return {
    ids: appDataReducer.ids,
    data: appDataReducer.data
  };
};

const mapDTP = {
  setData,
  selectCandidateIndex
};

export default connect(
  mapStateToProps,
  mapDTP
)(RankGraph);
