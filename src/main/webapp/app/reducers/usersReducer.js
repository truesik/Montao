import { fromJS } from 'immutable';

import * as constants from '../constants/userConstants';

const initialState = fromJS({
  subscribers: [],
  error: '',
  isAuthorized: false,
  username: '',
  userPath: ''
});

const usersReducer = (state = initialState, action) => {
  switch (action.type) {
    case constants.GET_USERS_REQUEST:
      return state;
    case constants.GET_USERS_SUCCESS:
      return state.set('subscribers', fromJS(action.payload));
    case constants.GET_USERS_FAILURE:
      return state.set('error', action.payload);
    case constants.LOG_IN_REQUEST:
      return state;
    case constants.LOG_IN_SUCCESS:
      return state;
    case constants.LOG_IN_FAILURE:
      return state.set('error', action.payload);
    case constants.LOG_OUT_REQUEST:
      return state;
    case constants.LOG_OUT_SUCCESS:
      return state.set('isAuthorized', false);
    case constants.LOG_OUT_FAILURE:
      return state.set('error', action.payload);
    case constants.ADD_USER_REQUEST:
      return state;
    case constants.ADD_USER_SUCCESS:
      return state.set('userPath', action.payload);
    case constants.ADD_USER_FAILUER:
      return state.set('error', action.payload);
    case constants.CHECK_AUTHORIZATION_REQUEST:
      return state;
    case constants.CHECK_AUTHORIZATION_SUCCESS:
      return state.set('isAuthorized', true).set('username', action.payload);
    case constants.CHECK_AUTHORIZATION_FAILURE:
      return state.set('isAuthorized', false).set('username', '');
    default:
      return state;
  }
};

export default usersReducer;
