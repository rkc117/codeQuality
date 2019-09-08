import EVENTS from "./actionType";

export const submitGitRepo = data => {
  return {
    type: EVENTS.SUBMIT_GIT_REPO,
    payload: data
  };
};
export const resetId = () => {
  return {
    type: EVENTS.SUBMIT_GIT_REPO_IDS,
    payload: null
  };
};

export const setData = data => {
  return {
    type: EVENTS.SET_DATA,
    payload: data
  };
};
export const selectCandidateIndex = data => {
  return {
    type: EVENTS.SELECT_CANDIDATE_INDEX,
    payload: data
  };
};
