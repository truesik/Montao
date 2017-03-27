import { SubmissionError, reset } from 'redux-form';

import { getCookie } from '../utils/cookie';
import * as constants from '../constants/channelConstants';
import * as viewActions from './ViewActions';

export const getChannels = (communityTitle) => {
  return (dispatch) => {
    dispatch({
      type: constants.GET_CHANNELS_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
    const request = new Request('/api/channels/get_channels', {
      method: 'POST',
      body: `communityTitle=${communityTitle}`,
      headers: headers,
      credentials: 'same-origin'
    });

    return fetch(request)
      .then(response => {
        if (response.status < 200 || response.status >= 300) {
          const error = new Error(response.statusText);
          error.response = response.json();
          throw error;
        }
        return response.json();
      })
      .then(response => {
        dispatch({
          type: constants.GET_CHANNELS_SUCCESS,
          payload: response
        });
      })
      .catch(error => {
        dispatch({
          type: constants.GET_CHANNELS_FAILURE,
          payload: error
        });
      });
  };
};

export const add = (channel) => {
  return dispatch => {
    dispatch({
      type: constants.ADD_CHANNEL_REQUEST
    });
    const headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
    const request = new Request('/api/channels', {
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
          throw error;
        } else {
          return response;
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
        });
      });
  };
};
