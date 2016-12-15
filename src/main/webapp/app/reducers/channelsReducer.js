import {fromJS} from 'immutable';

import * as constants from '../constants/channelConstants';

const initialState = fromJS({
    channels: [],
    currentChannelTitle: '',
    error: '',
    channelPath: ''
});

const channelsReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.GET_CHANNELS_REQUEST:
            return state;
        case constants.GET_CHANNELS_SUCCESS:
            return state.set('channels', fromJS(action.payload));
        case constants.GET_CHANNELS_FAILURE:
            return state.set('error', action.payload);
        case constants.GET_LAST_OPENED_CHANNEL_REQUEST:
            return state;
        case constants.GET_LAST_OPENED_CHANNEL_SUCCESS:
            return state.set('currentChannelTitle', action.payload);
        case constants.GET_LAST_OPENED_CHANNEL_FAILURE:
            return state.set('error', action.payload);
        case constants.SET_CURRENT_CHANNEL:
            return state.set('currentChannelTitle', action.payload);
        case constants.ADD_CHANNEL_REQUEST:
            return state;
        case constants.ADD_CHANNEL_SUCCESS:
            return state.set('channelPath', action.payload);
        case constants.ADD_CHANNEL_FAILURE:
            return state.set('error', action.payload);
        default:
            return state;
    }
};

export default channelsReducer;