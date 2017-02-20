import * as actionTypes from '../constants/searchConstants';
import { getCookie } from '../utils/cookie';

export const search = (query) => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SEARCH_REQUEST
    });
    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
    const request = new Request('/api/search', {
      method: 'POST',
      body: `q=${query}`,
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
          type: actionTypes.SEARCH_SUCCESS,
          payload: response
        });
      })
      .catch(error => {
        dispatch({
          type: actionTypes.SEARCH_FAILURE,
          payload: error
        });
      });
  };
};
