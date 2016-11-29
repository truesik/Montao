import * as actionTypes from "../constants/userConstants";
import * as viewActions from "./ViewActions";
import {SubmissionError} from "redux-form";

export const getUsers = (communityTitle) => {
    return (dispatch) => {
        dispatch({
            type: actionTypes.GET_USERS_REQUEST
        });
        $
            .ajax({
                url: '/api/user/get_subscribed_users?communityTitle=' + communityTitle,
                type: 'post',
                // headers: headers
            })
            .then((response, status, xhr) => {
                dispatch({
                    type: actionTypes.GET_USERS_SUCCESS,
                    payload: response
                })
            })
            .fail((xhr, status, error) => {
                dispatch({
                    type: actionTypes.GET_USERS_FAILURE,
                    payload: error
                })
            })
    };
};

export const logIn = (userCredentials) => {
    return dispatch => {
        dispatch({
            type: actionTypes.LOG_IN_REQUEST
        });
        let data = `username=${userCredentials.username}&password=${userCredentials.password}`;
        // build headers
        let headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
        // build request
        let request = new Request('/login', {
            method: 'POST',
            body: data,
            headers: headers,
            credentials: 'same-origin'
        });
        return fetch(request)
            .then(response => {
                if (response.status != 200) {
                    let error = new Error(response.statusText);
                    error.response = response;
                    throw error;
                } else {
                    dispatch({
                        type: actionTypes.LOG_IN_SUCCESS
                    });
                    // get username
                    dispatch(checkAuthorization());
                    // close log in dialog
                    dispatch(viewActions.hideLogInDialog())
                }
            })
            .catch((error) => {
                dispatch({
                    type: actionTypes.LOG_IN_FAILURE,
                    payload: error
                });
                throw new SubmissionError({
                    _error: 'Login failed!'
                })
            });
    }
};

export const logOut = () => {
    return (dispatch) => {
        dispatch({
            type: actionTypes.LOG_OUT_REQUEST
        });
        $
            .ajax({
                url: '/logout',
                type: 'post',
            })
            .then((response, status, xhr) => {
                dispatch({
                    type: actionTypes.LOG_OUT_SUCCESS
                })
            })
            .fail((xhr, status, error) => {
                dispatch({
                    type: actionTypes.LOG_OUT_FAILURE,
                    payload: error
                })
            })
    }
};

export const checkAuthorization = () => {
    return (dispatch) => {
        dispatch({
            type: actionTypes.CHECK_AUTHORIZATION_REQUEST
        });
        $
            .ajax({
                url: '/api/user/check_authorization',
                type: 'get'
            })
            .then((response, status, xhr) => {
                dispatch({
                    type: actionTypes.CHECK_AUTHORIZATION_SUCCESS,
                    payload: response
                })
            })
            .fail((xhr, status, error) => {
                dispatch({
                    type: actionTypes.CHECK_AUTHORIZATION_FAILURE,
                    payload: error
                })
            })
    }
};

export const addUser = (user) => {
    return (dispatch) => {
        dispatch({
            type: actionTypes.ADD_USER_REQUEST
        });
        let headers = new Headers();
        headers.append('Content-Type', 'application/json; charset=utf-8');
        let request = new Request('/api/user/add', {
            method: 'POST',
            body: JSON.stringify(user),
            headers: headers,
            credentials: 'same-origin'
        });
        return fetch(request)
            .then(response => {
                if (response.status != 201) {
                    const error = new Error(response.statusText);
                    error.response = response;
                    throw error
                } else {
                    return response
                }
            })
            .then(response => {
                const location = response.headers.get('location');
                dispatch({
                    type: actionTypes.ADD_USER_SUCCESS,
                    payload: location
                });
                dispatch(viewActions.hideSignUpDialog());
            })
            .catch(error => {
                dispatch({
                    type: actionTypes.ADD_USER_FAILUER,
                    payload: error
                });
                throw new SubmissionError({
                    _error: 'Server failure!'
                })
            })
    }
};