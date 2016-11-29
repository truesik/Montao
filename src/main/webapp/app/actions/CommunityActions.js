import * as actionTypes from "../constants/communityConstants";
import * as viewActions from "./ViewActions";
import {SubmissionError, reset} from "redux-form";

// var csrfToken = csrf;
// var csrfHeader = 'X-CSRF-TOKEN';
// var headers = {};
// headers[csrfHeader] = csrfToken;

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
                // headers: headers
            })
            .then((communities, status, xhr) => {
                dispatch({
                    type: actionTypes.GET_COMMUNITIES_SUCCESS,
                    payload: communities
                })
            })
            .fail((xhr, status, error) => {
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
                // headers: headers
            })
            .then((response, status, xhr) => {
                dispatch({
                    type: actionTypes.JOIN_SUCCESS,
                    payload: response
                })
            })
            .fail((xhr, status, error) => {
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
                // headers: headers
            })
            .then((response, status, xhr) => {
                dispatch({
                    type: actionTypes.LEAVE_SUCCESS,
                    payload: response
                })
            })
            .fail((xhr, status, error) => {
                dispatch({
                    type: actionTypes.LEAVE_FAILURE,
                    payload: error
                })
            })
    }
};

export const add = (community) => {
    return (dispatch) => {
        dispatch({
            type: actionTypes.ADD_COMMUNITY_REQUEST
        });

        const headers = new Headers();
        headers.append('Content-Type', 'application/json; charset=utf-8');

        const request = new Request('/api/community/add', {
            method: 'POST',
            body: JSON.stringify(community),
            headers: headers,
            credentials: 'same-origin'
        });
        return fetch(request)
            .then(response => {
                if (response.status == 201) {
                    const location = response.headers.get('location');
                    dispatch({
                        type: actionTypes.ADD_COMMUNITY_SUCCESS,
                        payload: location
                    });
                    dispatch(viewActions.hideAddCommunityDialog());
                    dispatch(reset('addCommunityForm'));
                } else {
                    const error = new Error(response.statusText);
                    error.response = response;
                    throw error;
                }
            })
            .catch(error => {
                dispatch({
                    type: actionTypes.ADD_COMMUNITY_FAILURE,
                    payload: error
                });
                throw new SubmissionError({
                    _error: 'Creation failed!'
                })
            });
    }
};