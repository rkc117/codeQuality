import React, { Component } from "react";
import { Bar } from "react-chartjs-2";
import "./CyclomaticsGraph.scss";
import routeConfig from "../../routeConfig";

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

class CyclomaticsGraph extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  getGraphData = cyclomatics => {
    let tempName = [];
    let tempScore = [];
    let graphConfig = {
      labels: [],
      datasets: [
        {
          label: "Cyclomatics complexity",
          backgroundColor: "rgba(75,192,192,0.4)",
          borderColor: "rgba(75,192,192,1)",
          hoverBackgroundColor: "rgba(75,192,19,1)",
          hoverBorderColor: "red",
          hoverBorderWidth: 2,
          data: []
        }
      ]
    };
    cyclomatics.file.map(obj => {
      let tempFiles = obj.fileName.split("/");
      tempName.push(tempFiles[tempFiles.length - 1]);
      tempScore.push(obj.avgCCN);
      return "";
    });
    graphConfig.labels = tempName;
    graphConfig.datasets[0].data = tempScore;
    return graphConfig;
  };

  onclickGraph = clickedGraph => {
    const { history, cyclomatics } = this.props;
    if (clickedGraph[0] === undefined) {
      return;
    }
    console.log("clickedGraph", cyclomatics.file[clickedGraph[0]._index]);
    // history.push(routeConfig.error, {
    //   type: "cyclomatics",
    //   data: cyclomatics.file[clickedGraph[0]._index]
    // });
  };
  render() {
    const { cyclomatics } = this.props;
    let tempGraphConfig = this.getGraphData(cyclomatics);
    let tempLength = tempGraphConfig.datasets[0].data.length;

    return (
      <div>
        {tempLength > 0 ? (
          <div style={styles}>
            <table style={{ width: "800px" }}>
              <tbody>
                <div className="CyclomaticsGraph">
                  <h2>Cyclomatics Graph</h2>
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

export default CyclomaticsGraph;
