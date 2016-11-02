import * as actionTypes from "../constants/communityConstants";

const initialState = {
    communities: [],
    error: ''
};

const communitiesReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.GET_COMMUNITIES_REQUEST:
            return {
                ...state
            };
        case actionTypes.GET_COMMUNITIES_SUCCESS:
            return {
                ...state,
                communities: action.payload
            };
        case actionTypes.GET_COMMUNITIES_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        default:
            return state;
    }
};

export default communitiesReducer;