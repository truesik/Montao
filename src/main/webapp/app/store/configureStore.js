import { createStore, applyMiddleware } from "redux";
import reducers from '../reducers';
import thunk from 'redux-thunk';
import createLogger from "redux-logger";
import {websocketMiddleware} from "./websocketMiddleware";

export default function configureStore(initialState) {
    var logger = createLogger();
    return createStore(
        reducers,
        initialState,
        applyMiddleware(thunk, websocketMiddleware, logger)
    );
}