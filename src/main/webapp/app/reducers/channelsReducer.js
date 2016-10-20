import {GET_CHANNELS_REQUEST, GET_CHANNELS_SUCCESS, GET_CHANNELS_FAILURE} from "../constants/Channel";
const initialState = {
    channels: [],
    error: '',
    isFetching: false
};

const channelsReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_CHANNELS_REQUEST:
            return {
                ...state,
                isFetching: true
            };
        case GET_CHANNELS_SUCCESS:
            return {
                ...state,
                channels: action.payload,
                isFetching: false
            };
        case GET_CHANNELS_FAILURE:
            return {
                ...state,
                error: action.payload,
                isFetching: false
            };
        default:
            return state;
    }
};

export default channelsReducer;