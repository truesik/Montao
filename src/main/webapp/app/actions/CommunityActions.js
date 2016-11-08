import * as actionTypes from "../constants/communityConstants";

var csrfToken = csrf;
var csrfHeader = 'X-CSRF-TOKEN';
var headers = {};
headers[csrfHeader] = csrfToken;

export const getCommunities = (startRowPosition) => {
    return dispatch => {
        dispatch({
            type: actionTypes.GET_COMMUNITIES_REQUEST
        });
        $
            .ajax({
                url: '/api/community/get_all',
                type: 'post',
                data: {startRowPosition: startRowPosition},
                headers: headers
            })
            .then(communities => {
                dispatch({
                    type: actionTypes.GET_COMMUNITIES_SUCCESS,
                    payload: communities
                })
            })
            .fail(error => {
                dispatch({
                    type: actionTypes.GET_COMMUNITIES_FAILURE,
                    payload: error
                })
            })
    }
};

export const join = (communityTitle) => {
    return dispatch => {
        dispatch({
            type: actionTypes.JOIN_REQUEST
        });
        $
            .ajax({
                url: '/api/community/join',
                type: "post",
                cache: false,
                data: {communityTitle: communityTitle},
                headers: headers
            })
            .then(response => {
                dispatch({
                    type: actionTypes.JOIN_SUCCESS,
                    payload: response
                })
            })
            .fail(error => {
                dispatch({
                    type: actionTypes.JOIN_FAILURE,
                    payload: error
                })
            })
    }
};

export const leave = (communityTitle) => {
    return dispatch => {
        dispatch({
            type: actionTypes.LEAVE_REQUEST
        });
        $
            .ajax({
                url: '/api/community/leave',
                type: "post",
                cache: false,
                data: {communityTitle: communityTitle},
                headers: headers
            })
            .then(response => {
                dispatch({
                    type: actionTypes.LEAVE_SUCCESS,
                    payload: response
                })
            })
            .fail(error => {
                dispatch({
                    type: actionTypes.LEAVE_FAILURE,
                    payload: error
                })
            })
    }
};