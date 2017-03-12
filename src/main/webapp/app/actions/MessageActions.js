import { getCookie } from '../utils/cookie';
import * as constants from '../constants/messageConstants';

export const getMessages = (communityTitle, channelTitle, startRowPosition) => {
  return (dispatch) => {
    dispatch({
      type: constants.GET_MESSAGES_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));

    const query = [
      `communityTitle=${communityTitle}`,
      `channelTitle=${channelTitle}`,
      `startRowPosition=${startRowPosition}`
    ].join('&');

    const request = new Request(`/api/messages?${query}`, {
      method: 'GET',
      headers: headers,
      credentials: 'same-origin'
    });

    return fetch(request)
      .then(response => {
        if (response.status != 200) {
          const error = new Error(response.statusText);
          error.response = response;
          throw error;
        }
        return response.json();
      })
      .then(messages => {
        dispatch({
          type: constants.GET_MESSAGES_SUCCESS,
          payload: messages
        });
      })
      .catch(error => {
        dispatch({
          type: constants.GET_MESSAGES_FAILURE,
          payload: error
        });
      });
  };
};

export const getOldestMessages = (communityTitle, channelTitle, startRowPosition) => {
  return (dispatch) => {
    dispatch({
      type: constants.GET_OLDEST_MESSAGES_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));

    const query = [
      `communityTitle=${communityTitle}`,
      `channelTitle=${channelTitle}`,
      `startRowPosition=${startRowPosition}`
    ].join('&');

    const request = new Request(`/api/messages?${query}`, {
      method: 'GET',
      headers: headers,
      credentials: 'same-origin'
    });

    return fetch(request)
      .then(response => {
        if (response.status != 200) {
          const error = new Error(response.statusText);
          error.response = response;
          throw error;
        }
        return response.json();
      })
      .then(messages => {
        dispatch({
          type: constants.GET_OLDEST_MESSAGES_SUCCESS,
          payload: messages
        });
      })
      .catch(error => {
        dispatch({
          type: constants.GET_OLDEST_MESSAGES_FAILURE,
          payload: error
        });
      });
  };
};

export const getMessage = (message) => {
  return (dispatch) => {
    dispatch({
      type: constants.GET_MESSAGE_SUCCESS,
      payload: message
    });
  };
};

export const sendMessage = (communityTitle, channelTitle, message) => {
  return (dispatch) => {
    dispatch({
      type: constants.SEND_MESSAGE_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));

    const request = new Request('/api/messages', {
      method: 'POST',
      body: `text=${message}&communityTitle=${communityTitle}&channelTitle=${channelTitle}`,
      headers: headers,
      credentials: 'same-origin'
    });

    return fetch(request)
      .then(response => {
        if (response.status != 201) {
          const error = new Error(response.statusText);
          error.response = response;
          throw error;
        }
        return response;
      })
      .then(response => {
        const location = response.headers.get('location');
        dispatch({
          type: constants.SEND_MESSAGE_SUCCESS,
          payload: location
        });
      })
      .catch(error => {
        dispatch({
          type: constants.SEND_MESSAGE_FAILURE,
          payload: error
        });
      });
  };
};
