// import $ from 'jquery'
import * as constants from '../constants/messageConstants'

var csrfToken = csrf;
var csrfHeader = 'X-CSRF-TOKEN';
var headers = {};
headers[csrfHeader] = csrfToken;

export const getMessages = (communityTitle, channelTitle, startRowPosition) => {
    return (dispatch) => {
        dispatch({
            type: constants.GET_MESSAGES_REQUEST
        });
        $
            .ajax({
                url: '/api/message/get_messages?communityTitle=' + communityTitle + '&channelTitle=' + channelTitle + '&startRowPosition=' + startRowPosition,
                type: 'post',
                headers: headers
            })
            .then(messages => {
                dispatch({
                    type: constants.GET_MESSAGES_SUCCESS,
                    payload: messages
                })
            })
            .fail(error => {
                dispatch({
                    type: constants.GET_MESSAGES_FAILURE,
                    payload: error
                })
            })
    }
};

export const getOldestMessages = (communityTitle, channelTitle, startRowPosition) => {
    return (dispatch) => {
        dispatch({
            type: constants.GET_OLDEST_MESSAGES_REQUEST
        });
        $
            .ajax({
                url: '/api/message/get_messages?communityTitle=' + communityTitle + '&channelTitle=' + channelTitle + '&startRowPosition=' + startRowPosition,
                type: 'post',
                headers: headers
            })
            .then(messages => {
                dispatch({
                    type: constants.GET_OLDEST_MESSAGES_SUCCESS,
                    payload: messages
                })
            })
            .fail(error => {
                dispatch({
                    type: constants.GET_OLDEST_MESSAGES_FAILURE,
                    payload: error
                })
            })
    }
};

export const getMessage = () => {
    return (dispatch) => {

    }
};

export const sendMessage = (communityTitle, channelTitle, message) => {
    return (dispatch) => {
        dispatch({
            type: constants.SEND_MESSAGE_REQUEST
        });
        $
            .ajax({
                url: '/api/message/add?newMessage=' + message + '&communityTitle=' + communityTitle + '&channelTitle=' + channelTitle,
                type: "post",
                headers: headers
            })
            .then(
                dispatch({
                    type: constants.SEND_MESSAGE_SUCCESS
                })
            )
            .fail(error => {
                dispatch({
                    type: constants.SEND_MESSAGE_FAILURE,
                    payload: error
                })
            })
    }
};