import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import usersReducer from './usersReducer'
import channelsReducer from './channelsReducer';
import messagesReducer from './messagesReducer';
import websocketReducer from './websocketReducer';
import communitiesReducer from './communitiesReducer';
import viewReducer from './viewReducer';

export default combineReducers({
    usersReducer,
    channelsReducer,
    messagesReducer,
    websocketReducer,
    communitiesReducer,
    viewReducer,
    form: formReducer
})