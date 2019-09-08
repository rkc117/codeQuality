import { put, call, takeEvery, select } from "redux-saga/effects";
import axios from "axios";
import EVENTS from "../actionType";
// import davidService from "../../../../constants/davidServices";

import { assocPath, evolve, append, filter } from "ramda";

const urlConfig = {
  submitGitRepo: "http://localhost:8080/codeQuality/check"
};

function davidService(url, type, data) {
  if (type === "post") {
    return axios.post(url, data);
  } else if (type == "get") {
    return axios.get(url);
  }
}

function* submitGitRePOSaga(action) {
  try {
    const response = yield call(
      davidService,
      urlConfig.submitGitRepo,
      "post",
      action.payload
    );
    const {
      data: { data }
    } = response;
    console.log("userData", response);
    yield put({ type: EVENTS.SUBMIT_GIT_REPO_IDS, payload: data });
  } catch (error) {
    // yield put({ type: EVENTS.REQUEST_ERROR, error: error.message });
  } finally {
    // yield put({ type: EVENTS.SENDING_REQUEST, sending: false });
  }
}

export default function* appWatcherSaga() {
  yield takeEvery(EVENTS.SUBMIT_GIT_REPO, submitGitRePOSaga);
}
