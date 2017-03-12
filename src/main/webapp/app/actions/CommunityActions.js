import { SubmissionError, reset } from 'redux-form';

import { getCookie } from '../utils/cookie';
import * as actionTypes from '../constants/communityConstants';
import * as viewActions from './ViewActions';

export const getCommunities = (startRowPosition) => {
  return dispatch => {
    dispatch({
      type: actionTypes.GET_COMMUNITIES_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));

    const request = new Request('/api/communities/get_all', {
      method: 'POST',
      body: `startRowPosition=${startRowPosition}`,
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
      .then(communities => {
        dispatch({
          type: actionTypes.GET_COMMUNITIES_SUCCESS,
          payload: communities
        });
      })
      .catch(error => {
        dispatch({
          type: actionTypes.GET_COMMUNITIES_FAILURE,
          payload: error
        });
      });
  };
};

export const join = (communityTitle) => {
  return dispatch => {
    dispatch({
      type: actionTypes.JOIN_REQUEST,
      payload: communityTitle
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));

    const request = new Request('/api/communities/join', {
      method: 'POST',
      body: `communityTitle=${communityTitle}`,
      headers: headers,
      credentials: 'same-origin'
    });

    return fetch(request)
      .then(response => {
        if (response.status != 200) {
          const error = new Error(response.statusText);
          error.response = response.json();
          throw error;
        }
        return response.json();
      })
      .then(response => {
        dispatch({
          type: actionTypes.JOIN_SUCCESS,
          payload: response
        });
      })
      .catch(error => {
        dispatch({
          type: actionTypes.JOIN_FAILURE,
          payload: error
        });
      });
  };
};

export const leave = (communityTitle) => {
  return dispatch => {
    dispatch({
      type: actionTypes.LEAVE_REQUEST,
      payload: communityTitle
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));

    const request = new Request('/api/communities/leave', {
      method: 'POST',
      body: `communityTitle=${communityTitle}`,
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
      .then(response => {
        dispatch({
          type: actionTypes.LEAVE_SUCCESS,
          payload: response
        });
      })
      .catch(error => {
        dispatch({
          type: actionTypes.LEAVE_FAILURE,
          payload: error
        });
      });
  };
};

export const add = (community) => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.ADD_COMMUNITY_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
    const request = new Request('/api/communities', {
      method: 'POST',
      body: JSON.stringify(community),
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
          type: actionTypes.ADD_COMMUNITY_SUCCESS,
          payload: location
        });
        dispatch(viewActions.hideAddCommunityDialog());
        dispatch(reset('addCommunityForm'));
      })
      .catch(error => {
        dispatch({
          type: actionTypes.ADD_COMMUNITY_FAILURE,
          payload: error
        });
        throw new SubmissionError({
          _error: 'Creation failed!'
        });
      });
  };
};

export const checkSubscription = (communityTitle) => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.CHECK_SUBSCRIPTION_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
    const request = new Request('/api/communities/check_subscription', {
      method: 'POST',
      body: `communityTitle=${communityTitle}`,
      headers: headers,
      credentials: 'same-origin'
    });

    return fetch(request)
      .then(response => {
        if (response.status != 200) {
          const error = new Error(response.statusText);
          error.response = response;
          throw error;
        } else {
          return response.text();
        }
      })
      .then(response => {
        dispatch({
          type: actionTypes.CHECK_SUBSCRIPTION_SUCCESS,
          payload: response
        });
      })
      .catch(error => {
        dispatch({
          type: actionTypes.CHECK_SUBSCRIPTION_FAILURE,
          payload: error
        });
      });
  };
};

export const checkCommunity = (title) => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.CHECK_COMMUNITY_REQUEST
    });

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
    const request = new Request('/api/communities/get', {
      method: 'POST',
      body: `communityTitle=${title}`,
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
      .then(response => {
        dispatch({
          type: actionTypes.CHECK_COMMUNITY_SUCCESS,
          payload: response
        });
      })
      .catch(error => {
        dispatch({
          type: actionTypes.CHECK_COMMUNITY_FAILURE,
          payload: error
        });
      });
  };
};
