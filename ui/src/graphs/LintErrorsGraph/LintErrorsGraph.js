import React, { Component } from "react";
import { Bar } from "react-chartjs-2";
import "./LintErrorsGraph.scss";
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

class LintErrorsGraph extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  getGraphData = checkstyles => {
    let tempName = [];
    let tempScore = [];
    // {
    //   "_id": "/home/rkc/Briefcase/spotbugs/gitHubDownloadedRepos/AmbalviUsman/src/AdminPanel.java",
    //   "totalCounts": 41,
    //   "totalLines": "219"
    // }
    let graphConfig = {
      labels: [],
      datasets: [
        {
          label: "Styling (%)",
          backgroundColor: "rgba(75,192,192,0.4)",
          borderColor: "rgba(75,192,192,1)",
          hoverBackgroundColor: "rgba(75,192,19,1)",
          hoverBorderColor: "red",
          hoverBorderWidth: 2,
          data: []
        }
      ]
    };
    checkstyles.map(obj => {
      let tempFiles = obj._id.split("/");
      tempName.push(tempFiles[tempFiles.length - 1]);
      tempScore.push(Math.floor((obj.totalCounts / obj.totalLines) * 100));
      return "";
    });
    graphConfig.labels = tempName;
    graphConfig.datasets[0].data = tempScore;
    return graphConfig;
  };

  onclickGraph = clickedGraph => {
    const { history, checkstyles } = this.props;
    if (clickedGraph[0] === undefined) {
      return;
    }
    console.log("clickedGraph", checkstyles[clickedGraph[0]._index]);
    history.push(routeConfig.error, {
      type: "results",
      data: checkstyles[clickedGraph[0]._index]
    });
  };
  render() {
    const { checkstyles } = this.props;
    let tempGraphConfig = this.getGraphData(checkstyles);
    let tempLength = tempGraphConfig.datasets[0].data.length;
    return (
      <div>
        {tempLength > 0 ? (
          <div style={styles}>
            <table style={{ width: "800px" }}>
              <tbody>
                <div className="LintErrorsGraph">
                  <h2>Styling Graph</h2>
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

export default LintErrorsGraph;
