import { fromJS } from 'immutable';

import * as constants from '../constants/messageConstants';

const initialState = fromJS({
  messages: [],
  error: '',
  scrollBottom: false,
  startRowPosition: 0
});

const messagesReducer = (state = initialState, action) => {
  switch (action.type) {
    case constants.GET_MESSAGES_REQUEST:
      return state.update('messages', messages => messages.clear());
    case constants.GET_MESSAGES_SUCCESS:
      return state
        .set('messages', fromJS(action.payload))
        .set('startRowPosition', action.payload.length)
        .set('scrollBottom', true);
    case constants.GET_MESSAGES_FAILURE:
      return state.set('error', action.payload);
    case constants.GET_MESSAGE_SUCCESS:
      return state.update('messages', messages => messages.push(fromJS(action.payload)));
    case constants.GET_OLDEST_MESSAGES_REQUEST:
      return state;
    case constants.GET_OLDEST_MESSAGES_SUCCESS:
      return state
        .update('messages', messages => fromJS(action.payload).concat(messages))
        .update('startRowPosition', count => count + action.payload.length)
        .set('scrollBottom', false);
    case constants.GET_OLDEST_MESSAGES_FAILURE:
      return state.set('error', action.payload);
    case constants.SEND_MESSAGE_REQUEST:
      return state;
    case constants.SEND_MESSAGE_SUCCESS:
      return state;
    case constants.SEND_MESSAGE_FAILURE:
      return state.set('error', action.payload);
    default:
      return state;
  }
};

export default messagesReducer;