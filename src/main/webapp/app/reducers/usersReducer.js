import {GET_USERS_REQUEST, GET_USERS_SUCCESS, GET_USERS_FAILURE} from "../constants/User";

const initialState = {
    subscribers: [],
    error: '',
    isFetching: false
};

const usersReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_USERS_REQUEST:
            return {
                ...state,
                isFetching: true
            };
        case GET_USERS_SUCCESS:
            return {
                ...state,
                subscribers: action.payload,
                isFetching: false
            };
        case GET_USERS_FAILURE:
            return {
                ...state,
                error: action.payload,
                isFetching: false
            };
        default:
            return state;
    }
};

export default usersReducer