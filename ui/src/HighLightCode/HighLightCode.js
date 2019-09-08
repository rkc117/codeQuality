import React, { Component } from "react";
import SyntaxHighlighter from "react-syntax-highlighter";
import docco from "./doco";
import axios from "axios";
import Loading from "../Loading";

import "./Highlight.css";

const CODE = `const woah = fun => fun + 1;
const dude = woah(2) + 3;
function thisIsAFunction() {
  return [1,2,3].map(n => n + 1).filter(n !== 3);
}
console.log('making up fake code is really hard');

function itIs() {
  return 'no seriously really it is';
}`;

class HighLightCode extends Component {
  constructor(props) {
    super(props);
    this.state = { code: null, WARING: [], MESSAGES: {} };
  }
  componentDidMount() {
    const {
      history: { location }
    } = this.props;
    let WARING = [],
      MESSAGES = {};

    if (location.state !== undefined) {
      const {
        type,
        data,
        data: { _id }
      } = location.state;
      if (data[type] !== undefined) {
        data[type].map(obj => {
          WARING.push(parseInt(obj.lineNumber));
          MESSAGES[obj.lineNumber] = obj.message;
        });
      }
      let url = `http://localhost:8080/codeQuality/files?path=${_id}`;

      axios
        .get(url)
        .then(res => {
          const { data } = res;
          this.setState({ code: data, WARING, MESSAGES });
        })
        .catch(error => {
          console.log("error", error);
        });
    }
  }
  render() {
    const { WARING, MESSAGES, code } = this.state;
    const h1Style = {
      fontSize: 42,
      color: "aliceblue"
    };
    console.log("1111", WARING, MESSAGES);

    return (
      <div>
        {code !== null ? (
          <div>
            <h1 style={h1Style}>React SyntaxHighlighter</h1>
            <div style={{ paddingTop: 20, display: "flex" }}>
              <div className="codeWrapper">
                <SyntaxHighlighter
                  showLineNumbers
                  style={docco}
                  useInlineStyles={true}
                  wrapLines={true}
                  lineProps={lineNumber => {
                    let className = "codeLine ";
                    if (WARING.includes(lineNumber)) {
                      className += "warning ";
                    }
                    return {
                      className,
                      "data-msg": MESSAGES[lineNumber]
                    };
                  }}
                >
                  {code}
                </SyntaxHighlighter>
              </div>
            </div>
          </div>
        ) : (
          <Loading />
        )}
      </div>
    );
  }
}

export default HighLightCode;
