import * as constants from "../constants/channelConstants";
import {SubmissionError, reset} from "redux-form";
import * as viewActions from "./ViewActions";

export const getChannels = (communityTitle) => {
    return (dispatch) => {
        dispatch({
            type: constants.GET_CHANNELS_REQUEST
        });
        $
            .ajax({
                url: '/api/channel/get_channels?communityTitle=' + communityTitle,
                type: 'post',
                // headers: headers
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
                // headers: headers
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

export const add = (channel) => {
    return dispatch => {
        dispatch({
            type: constants.ADD_CHANNEL_REQUEST
        });
        const headers = new Headers();
        headers.append('Content-Type', 'application/json; charset=utf-8');
        const request = new Request('/api/channel/add', {
            method: 'POST',
            body: JSON.stringify(channel),
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
                    type: constants.ADD_CHANNEL_SUCCESS,
                    payload: location
                });
                dispatch(viewActions.hideAddChannelDialog());
                dispatch(reset('addChannelForm'));
            })
            .catch(error => {
                dispatch({
                    type: constants.ADD_CHANNEL_FAILURE,
                    payload: error
                });
                throw new SubmissionError({
                    _error: 'Creation failed!'
                })
            })
    }
};