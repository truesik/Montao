import * as constants from "../constants/userConstants";

const initialState = {
    subscribers: [],
    error: '',
    userListFetching: false,
    isAuthorized: false
};

const usersReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.GET_USERS_REQUEST:
            return {
                ...state,
                userListFetching: true
            };
        case constants.GET_USERS_SUCCESS:
            return {
                ...state,
                subscribers: action.payload,
                userListFetching: false
            };
        case constants.GET_USERS_FAILURE:
            return {
                ...state,
                error: action.payload,
                userListFetching: false
            };
        case constants.LOG_IN_REQUEST:
            return {
                ...state
            };
        case constants.LOG_IN_SUCCESS:
            return {
                ...state,
                isAuthorized: true
            };
        case constants.LOG_IN_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        case constants.LOG_OUT_REQUEST:
            return {
                ...state
            };
        case constants.LOG_OUT_SUCCESS:
            return {
                ...state,
                isAuthorized: false
            };
        case constants.LOG_OUT_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        default:
            return state;
    }
};

export default usersReducer