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
        case actionTypes.JOIN_REQUEST:
            return {
                ...state
            };
        case actionTypes.JOIN_SUCCESS:
            return {
                ...state,
                communities: state.communities.map(community => communityReducer(community, action))
            };
        case actionTypes.JOIN_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        case actionTypes.LEAVE_REQUEST:
            return {
                ...state
            };
        case actionTypes.LEAVE_SUCCESS:
            return {
                ...state,
                communities: state.communities.map(community => communityReducer(community, action))
            };
        case actionTypes.LEAVE_FAILURE:
            return {
                ...state,
                error: action.payload
            };
        default:
            return state;
    }
};

const communityReducer = (community, action) => {
    if (community.id === action.payload.id) {
        return {
            ...community,
            subscribed: action.payload.subscribed
        }
    }
    return community;
};

export default communitiesReducer;