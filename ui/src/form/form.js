import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";
import {
  Typography,
  Paper,
  Grid,
  Button,
  CssBaseline
} from "@material-ui/core";
import { keys } from "ramda";
import { connect } from "react-redux";
import { submitGitRepo, resetId, setData } from "../reducer/actions";
import routeConfig from ".././routeConfig";

class DataForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      values: {
        v1: null,
        v2: null,
        v3: null,
        v4: null,
        v5: null
      }
    };
  }

  componentDidMount() {
    this.props.resetId();
    this.props.setData(null);
  }
  onSubmit = () => {
    const { values } = this.state;
    const { submitGitRepo, history } = this.props;
    let tempValues = Object.values(values).filter(value => value !== null);
    if (tempValues.length == 0) {
      return;
    }
    submitGitRepo({
      gitRepos: Object.values(values).filter(value => value !== null)
    });
    history.push(routeConfig.profile);
  };
  validate = values => {
    const errors = {};
    if (!values.firstName) {
      errors.firstName = "Required";
    }
    if (!values.lastName) {
      errors.lastName = "Required";
    }

    return errors;
  };
  handleChange = ({ currentTarget }) => {
    const { name, value } = currentTarget;
    const { values } = this.state;
    this.setState({
      values: { ...values, [name]: value }
    });
  };
  render() {
    const { values } = this.state;
    return (
      <div style={{ padding: 16, margin: "auto", maxWidth: 600 }}>
        <CssBaseline />
        <Typography variant="h4" align="center" component="h1" gutterBottom>
          Analyzer
        </Typography>
        <Paper style={{ padding: 16 }}>
          <Grid container alignItems="flex-start" spacing={2}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                id={"v1"}
                value={values.v1}
                onChange={this.handleChange}
                name="v1"
                type="text"
                label="Git url 1"
                placeholder="Git ur 1"
              />

              <TextField
                fullWidth
                id={"v2"}
                value={values.v2}
                onChange={this.handleChange}
                name="v2"
                type="text"
                label="Git url 2"
                placeholder="Git ur 2"
              />

              <TextField
                fullWidth
                id={"v3"}
                value={values.v3}
                onChange={this.handleChange}
                name="v3"
                type="text"
                label="Git url 3"
                placeholder="Git ur 3"
              />

              <TextField
                fullWidth
                id={"v4"}
                value={values.v4}
                onChange={this.handleChange}
                name="v4"
                type="text"
                label="Git url 4"
                placeholder="Git ur 4"
              />
            </Grid>

            <Grid
              container
              direction="row"
              justify="flex-start"
              alignItems="center"
              xs={12}
            >
              <Grid item xs={3} style={{ marginTop: 16 }}>
                <Button
                  variant="contained"
                  color="primary"
                  type="submit"
                  onClick={this.onSubmit}
                  // disabled={submitting}
                >
                  Submit
                </Button>
              </Grid>
              <Grid item xs={3} style={{ marginTop: 16 }}>
                {/* <Button
                  type="button"
                  variant="contained"
                  onClick={() => console.log("reset")}
                  // disabled={submitting || pristine}
                >
                  Reset
                </Button> */}
              </Grid>
            </Grid>
          </Grid>
        </Paper>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  state
});

const mapDTP = {
  submitGitRepo,
  resetId,
  setData
};

export default connect(
  mapStateToProps,
  mapDTP
)(DataForm);
