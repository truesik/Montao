import SockJS from 'sockjs-client';
import { Stomp } from 'stompjs/lib/stomp';

import { getMessage } from '../actions/MessageActions';

import * as actionType from '../constants/websocketConstants';

const websocket = {
  sock: null,
  stomp: null,
  messageBoxSubscription: null
};

export function websocketMiddleware(store) {
  return (next) => action => {
    if (action.type === actionType.CONNECT_TO_WEBSOCKET_REQUEST) {
      websocket.sock = new SockJS('/api/websocket');
      websocket.stomp = Stomp.over(websocket.sock);
      websocket.stomp.connect('guest', 'guest',
        () => {
          store.dispatch({
            type: actionType.CONNECT_TO_WEBSOCKET_SUCCESS
          });
        },
        (error) => {
          store.dispatch({
            type: actionType.CONNECT_TO_WEBSOCKET_FAILURE,
            payload: error
          });
        });
    }
    if (websocket.stomp && action.type === actionType.SUBSCRIBE_TO_TOPIC_REQUEST) {
      websocket.messageBoxSubscription = websocket.stomp.subscribe(action.topic, (incoming) => {
        const message = JSON.parse(incoming.body);
        store.dispatch(getMessage(message));
      });
    }
    if (websocket.stomp && action.type === actionType.UNSUBSCRIBE_REQUEST) {
      if (websocket.messageBoxSubscription) {
        websocket.messageBoxSubscription.unsubscribe();
      }
    }
    if (websocket.stomp && action.type === actionType.DISCONNECT_REQUEST) {
      websocket.stomp.disconnect(() => {
        store.dispatch({
          type: actionType.DISCONNECT_SUCCESS
        });
      });
    }
    return next(action);
  };
}
