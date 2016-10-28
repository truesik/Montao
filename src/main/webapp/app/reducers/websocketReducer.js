import * as actionTypes from '../constants/websocketConstants';

const initialState = {
    error: '',
    isConnected: false
};

const websocketReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.CONNECT_TO_WEBSOCKET_REQUEST:
            return {
                ...state
            };
        case actionTypes.CONNECT_TO_WEBSOCKET_SUCCESS:
            return {
                ...state,
                isConnected: true
            };
        case actionTypes.CONNECT_TO_WEBSOCKET_FAILURE:
            return {
                ...state,
                isConnected: false,
                error: action.payload
            };
        default:
            return state;
    }
};

export default websocketReducer;