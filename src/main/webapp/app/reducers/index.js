import { combineReducers } from "redux";
import usersReducer from './usersReducer'
import channelsReducer from "./channelsReducer";

export default combineReducers({
    usersReducer,
    channelsReducer
})