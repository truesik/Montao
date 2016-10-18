import {GET_CHANNELS} from "../constants/Channel";
const initialState = {
    channels: []
};

const channelsReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_CHANNELS:
            return {
                ...state,
                channels: action.payload
            };
        default:
            return state;
    }
};

export default channelsReducer;