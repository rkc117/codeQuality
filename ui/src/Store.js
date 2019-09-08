import { createStore, applyMiddleware } from "redux";
import appData from "./reducer/AllReducer";
import createSagaMiddleware from "redux-saga";
import rootSaga from "./reducer/saga";
import StateLoader from "./stateLoader";
import { composeWithDevTools } from "redux-devtools-extension";

const stateLoader = new StateLoader();
const sagaMiddleware = createSagaMiddleware();

const composeEnhancers = composeWithDevTools({ trace: true, traceLimit: 25 });

const middleware = [sagaMiddleware];

const Store = createStore(
  appData,
  stateLoader.loadState(),
  process.env.NODE_ENV === "production"
    ? applyMiddleware(...middleware)
    : composeEnhancers(applyMiddleware(...middleware))
);
Store.subscribe(() => {
  stateLoader.saveState(Store.getState());
});
sagaMiddleware.run(rootSaga);
export default Store;
