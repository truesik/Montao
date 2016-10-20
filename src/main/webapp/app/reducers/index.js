import { combineReducers } from "redux";
import usersReducer from './usersReducer'
import channelsReducer from "./channelsReducer";
import messagesReducer from "./messagesReducer";

export default combineReducers({
    usersReducer,
    channelsReducer,
    messagesReducer
})