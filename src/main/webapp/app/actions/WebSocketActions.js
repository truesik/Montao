import * as actionTypes from '../constants/websocketConstants';

export const connectToWebsocket = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.CONNECT_TO_WEBSOCKET_REQUEST
    });
  };
};

export const subscribeToTopic = (currentCommunityTitle, currentChannelTitle) => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SUBSCRIBE_TO_TOPIC_REQUEST,
      topic: '/api/topic/' + currentCommunityTitle + '/' + currentChannelTitle
    });
  };
};

export const unsubscribe = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.UNSUBSCRIBE_REQUEST
    });
  };
};

export const disconnect = () => {
  return dispatch => {
    dispatch({
      type: actionTypes.DISCONNECT_REQUEST
    });
  };
};
