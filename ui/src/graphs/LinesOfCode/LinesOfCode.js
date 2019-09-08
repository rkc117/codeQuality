import React, { Component } from "react";
import { Bar } from "react-chartjs-2";
import "./LinesOfCode.scss";
import routeConfig from "../../routeConfig";
import axios from "axios";

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

class LinesOfCode extends Component {
  constructor(props) {
    super(props);
    this.state = { lines_code: [] };
  }
  componentDidMount() {
    const { ids } = this.props;
    let url = `http://localhost:8080/codeQuality/files/${ids}`;
    axios
      .get(url)
      .then(res => {
        const {
          data: { data }
        } = res;
        this.setState({ lines_code: data });
      })
      .catch(error => {
        console.log("error", error);
      });
  }
  getGraphData = lines_code => {
    let tempName = [];
    let tempScore = [];
    // {
    //   id:
    //     "/home/rkc/Briefcase/spotbugs/gitHubDownloadedRepos/AmbalviUsman/src/searchCashier.java",
    //   requestId: "5d73e057216d9130b5ea8fc1",
    //   name: "searchCashier.java",
    //   totalNumberLines: "68"
    // },
    let graphConfig = {
      labels: [],
      datasets: [
        {
          label: "Line of Codes",
          backgroundColor: "rgba(75,192,192,0.4)",
          borderColor: "rgba(75,192,192,1)",
          hoverBackgroundColor: "rgba(75,192,19,1)",
          hoverBorderColor: "red",
          hoverBorderWidth: 2,
          data: []
        }
      ]
    };
    lines_code.map(obj => {
      let tempFiles = obj.id.split("/");
      tempName.push(tempFiles[tempFiles.length - 1]);
      tempScore.push(obj.totalNumberLines);
      return "";
    });
    graphConfig.labels = tempName;
    graphConfig.datasets[0].data = tempScore;
    return graphConfig;
  };

  onclickGraph = clickedGraph => {
    const { history } = this.props;
    if (clickedGraph[0] === undefined) {
      return;
    }
    // console.log("clickedGraph", lines_code[clickedGraph[0]._index]);
    // history.push(routeConfig.error, {
    //   type: "LinesOfCode",
    //   data: lines_code[clickedGraph[0]._index]
    // });
  };
  render() {
    const { lines_code } = this.state;
    let tempGraphConfig = this.getGraphData(lines_code);
    let tempLength = tempGraphConfig.datasets[0].data.length;
    return (
      <div>
        {tempLength > 0 ? (
          <div style={styles}>
            <table style={{ width: "800px" }}>
              <tbody>
                <div className="LinesOfCode">
                  <h2>Lines of Code Graph</h2>
                  <Bar
                    ref="chart"
                    data={tempGraphConfig}
                    onElementsClick={this.onclickGraph}
                    options={options}
                  />
                </div>
              </tbody>
            </table>
          </div>
        ) : null}
      </div>
    );
  }
}

export default LinesOfCode;
