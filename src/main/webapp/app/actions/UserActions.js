import * as actionTypes from "../constants/userConstants";

var csrfToken = csrf;
var csrfHeader = 'X-CSRF-TOKEN';
var headers = {};
headers[csrfHeader] = csrfToken;

export const getUsers = (communityTitle) => {
    return (dispatch) => {
        dispatch({
            type: actionTypes.GET_USERS_REQUEST
        });
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $
            .ajax({
                url: '/api/user/get_subscribed_users?communityTitle=' + communityTitle,
                type: 'post',
                headers: headers
            })
            .then(response => {
                dispatch({
                    type: actionTypes.GET_USERS_SUCCESS,
                    payload: response
                })
            })
            .fail(error => {
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
        $
            .ajax({
                url: '/login',
                type: 'post',
                data: data,
                headers: headers
            })
            .then(response => {
                dispatch({
                    type: actionTypes.LOG_IN_SUCCESS
                })
            })
            .fail(error => {
                dispatch({
                    type: actionTypes.LOG_IN_FAILURE,
                    payload: error
                })
            })
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
                headers: headers
            })
            .then(response => {
                dispatch({
                    type: actionTypes.LOG_OUT_SUCCESS
                })
            })
            .fail(error => {
                dispatch({
                    type: actionTypes.LOG_OUT_FAILURE,
                    payload: error
                })
            })
    }
};