// import $ from 'jquery'
import * as constants from "../constants/channelConstants";

var csrfToken = csrf;
var csrfHeader = 'X-CSRF-TOKEN';
var headers = {};
headers[csrfHeader] = csrfToken;

export const getChannels = (communityTitle) => {
    return (dispatch) => {
        dispatch({
            type: constants.GET_CHANNELS_REQUEST
        });
        $
            .ajax({
                url: '/api/channel/get_channels?communityTitle=' + communityTitle,
                type: 'post',
                headers: headers
            })
            .then((response, status, xhr) => {
                dispatch({
                    type: constants.GET_CHANNELS_SUCCESS,
                    payload: response
                })
            })
            .fail((xhr, status, error) => {
                dispatch({
                    type: constants.GET_CHANNELS_FAILURE,
                    payload: error
                })
            })
    }
};

export const getLastOpenedChannel = (communityTitle) => {
    return dispatch => {
        dispatch({
            type: constants.GET_LAST_OPENED_CHANNEL_REQUEST
        });
        $
            .ajax({
                url: '/api/channel/get_last_opened_channel?communityTitle=' + communityTitle,
                type: 'post',
                headers: headers
            })
            .then((channel, status, xhr) => {
                dispatch({
                    type: constants.GET_LAST_OPENED_CHANNEL_SUCCESS,
                    payload: channel.title
                })
            })
            .fail((xhr, status, error) => {
                dispatch({
                    type: constants.GET_LAST_OPENED_CHANNEL_FAILURE,
                    payload: error
                })
            })
    }
};

export const setCurrentChannel = (channelTitle) => {
    return dispatch => {
        dispatch({
            type: constants.SET_CURRENT_CHANNEL,
            payload: channelTitle
        })
    }
};

export const addChannel = (channel) => {
    return dispatch => {
        dispatch({
            type: constants.ADD_CHANNEL_REQUEST
        });
        $
            .ajax({
                url: "/api/channel/add",
                type: "post",
                contentType: "application/json",
                date: JSON.stringify(channel),
                headers: headers
            })
            .then((response, status, xhr) => {
                dispatch({
                    type: constants.ADD_CHANNEL_SUCCESS
                })
            })
            .fail((xhr, status, error) => {
                dispatch({
                    type: constants.ADD_CHANNEL_FAILURE,
                    payload: error
                })
            })
    }
};