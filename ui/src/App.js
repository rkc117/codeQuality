import React from "react";
import routeConfig from "./routeConfig";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Form from "./form/form";
import RankGraph from "./graphs/RankGraph";
import Summary from "./Summary";
import HighLightCode from "./HighLightCode";

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path={routeConfig.root} exact component={Form} />
          <Route path={routeConfig.profile} exact component={RankGraph} />
          <Route path={routeConfig.summary} exact component={Summary} />
          <Route path={routeConfig.error} exact component={HighLightCode} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
