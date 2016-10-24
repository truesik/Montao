import * as constants from '../constants/messageConstants'

const initialState = {
    messages: [],
    error: '',
    scrollBottom: false,
    startRowPosition: 0
};

const messagesReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.GET_MESSAGES_REQUEST:
            return {
                ...state
            };
        case constants.GET_MESSAGES_SUCCESS:
            return {
                ...state,
                messages: action.payload,
                scrollBottom: true,
                startRowPosition: action.payload.length
            };
        case constants.GET_MESSAGES_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        case constants.GET_MESSAGE_REQUEST:
            return {
                ...state
            };
        case constants.GET_MESSAGE_SUCCESS:
            return {
                ...state,
                message: state.messages.concat(action.payload)
            };
        case constants.GET_MESSAGE_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        case constants.GET_OLDEST_MESSAGES_REQUEST:
            return {
                ...state
            };
        case constants.GET_OLDEST_MESSAGES_SUCCESS:
            return {
                ...state,
                messages: action.payload.concat(state.messages),
                scrollBottom: false,
                startRowPosition: state.messages.length + action.payload.length
            };
        case constants.GET_OLDEST_MESSAGES_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        case constants.SEND_MESSAGE_REQUEST:
            return {
                ...state
            };
        case constants.SEND_MESSAGE_SUCCESS:
            return {
                ...state
            };
        case constants.SEND_MESSAGE_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        default:
            return {
                ...state
            }
    }
};

export default messagesReducer;