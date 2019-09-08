import React, { Component } from "react";
import MaintainabilityGraph from "../graphs/MaintainabilityGraph";
import DupliCatLineOfCodeGraph from "../graphs/DupliCatLineOfCodeGraph";
import LintErrorsGraph from "../graphs/LintErrorsGraph";
import CyclomaticsGraph from "../graphs/CyclomaticsGraph";
import LinesOfCode from "../graphs/LinesOfCode";
import { connect } from "react-redux";

class Summary extends Component {
  render() {
    const { history, candidateIndex, data, ids } = this.props;
    if (data[candidateIndex] == undefined) {
      return <p style={{ textAlign: "center" }}>NO DATA FOUND</p>;
    }
    const { pmds, cpds, cyclomatics, checkstyles } = data[candidateIndex];
    return (
      <div>
        {cyclomatics && (
          <CyclomaticsGraph history={history} cyclomatics={cyclomatics} />
        )}
        {pmds && <MaintainabilityGraph history={history} pmds={pmds} />}
        {checkstyles && (
          <LintErrorsGraph history={history} checkstyles={checkstyles} />
        )}
        {cpds && <DupliCatLineOfCodeGraph history={history} cpds={cpds} />}
        <LinesOfCode history={history} ids={ids} />
      </div>
    );
  }
}

const mapStateToProps = ({ appDataReducer }) => {
  return {
    ids: appDataReducer.ids,
    data: appDataReducer.data,
    candidateIndex: appDataReducer.candidateIndex
  };
};

const mapDTP = {};

export default connect(
  mapStateToProps,
  mapDTP
)(Summary);
