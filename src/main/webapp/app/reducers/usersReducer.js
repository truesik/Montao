import * as constants from "../constants/userConstants";

const initialState = {
    subscribers: [],
    error: '',
    userListFetching: false,
    isAuthorized: false,
    username: '',
    userPath: ''
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
        case constants.ADD_USER_REQUEST:
            return {
                ...state
            };
        case constants.ADD_USER_SUCCESS:
            return {
                ...state,
                userPath: action.payload
            };
        case constants.ADD_USER_FAILUER:
            return {
                ...state,
                error: action.payload
            };
        case constants.CHECK_AUTHORIZATION_REQUEST:
            return {
                ...state
            };
        case constants.CHECK_AUTHORIZATION_SUCCESS:
            return {
                ...state,
                isAuthorized: true,
                username: action.payload
            };
        case constants.CHECK_AUTHORIZATION_FAILURE:
            return {
                ...state,
                isAuthorized: false,
                username: ''
            };
        default:
            return state;
    }
};

export default usersReducer