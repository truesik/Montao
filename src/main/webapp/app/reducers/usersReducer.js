import * as constants from "../constants/userConstants";

const initialState = {
    subscribers: [],
    error: '',
    userListFetching: false
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
        default:
            return state;
    }
};

export default usersReducer