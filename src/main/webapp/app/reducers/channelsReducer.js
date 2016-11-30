import * as constants from "../constants/channelConstants";

const initialState = {
    channels: [],
    currentChannelTitle: '',
    error: '',
    channelListFetching: false,
    channelPath: ''
};

const channelsReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.GET_CHANNELS_REQUEST:
            return {
                ...state,
                channelListFetching: true
            };
        case constants.GET_CHANNELS_SUCCESS:
            return {
                ...state,
                channels: action.payload,
                channelListFetching: false
            };
        case constants.GET_CHANNELS_FAILURE:
            return {
                ...state,
                error: action.payload,
                channelListFetching: false
            };
        case constants.GET_LAST_OPENED_CHANNEL_REQUEST:
            return {
                ...state
            };
        case constants.GET_LAST_OPENED_CHANNEL_SUCCESS:
            return {
                ...state,
                currentChannelTitle: action.payload
            };
        case constants.GET_LAST_OPENED_CHANNEL_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        case constants.SET_CURRENT_CHANNEL:
            return {
                ...state,
                currentChannelTitle: action.payload
            };
        case constants.ADD_CHANNEL_REQUEST:
            return {
                ...state
            };
        case constants.ADD_CHANNEL_SUCCESS:
            return {
                ...state,
                channelPath: action.payload
            };
        case constants.ADD_CHANNEL_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        default:
            return state;
    }
};

export default channelsReducer;