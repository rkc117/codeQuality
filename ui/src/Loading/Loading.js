import React from "react";

import LoadingGif from "../assets/loading.gif";
import "./Loading.css";

const Loading = () => (
  <div className="Loading">
    <div>
      <img src={LoadingGif} />
    </div>
    <p>Loading...</p>
  </div>
);

export default Loading;
