import { combineReducers } from "redux";
import usersReducer from './usersReducer'
import channelsReducer from "./channelsReducer";
import messagesReducer from "./messagesReducer";
import websocketReducer from "./websocketReducer";

export default combineReducers({
    usersReducer,
    channelsReducer,
    messagesReducer,
    websocketReducer
})