import { fromJS } from 'immutable';

import * as actionTypes from '../constants/websocketConstants';

const initialState = fromJS({
  error: '',
  isConnected: false
});

const websocketReducer = (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.CONNECT_TO_WEBSOCKET_REQUEST:
      return state;
    case actionTypes.CONNECT_TO_WEBSOCKET_SUCCESS:
      return state.set('isConnected', true);
    case actionTypes.CONNECT_TO_WEBSOCKET_FAILURE:
      return state.set('isConnected', false).set('error', action.payload);
    case actionTypes.DISCONNECT_REQUEST:
      return state;
    case actionTypes.DISCONNECT_SUCCESS:
      return state.set('isConnected', false);
    default:
      return state;
  }
};

export default websocketReducer;
