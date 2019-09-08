import EVENTS from "./actionType";
const initialState = {
  ids: "5d743789216d914478e10265",
  data: null,
  candidateIndex: null
};

export default function(state = initialState, action) {
  switch (action.type) {
    case EVENTS.SUBMIT_GIT_REPO_IDS:
      return { ...state, ids: action.payload };
    case EVENTS.SET_DATA:
      return { ...state, data: action.payload };
    case EVENTS.SELECT_CANDIDATE_INDEX:
      return { ...state, candidateIndex: action.payload };
    default:
      return state;
  }
}
