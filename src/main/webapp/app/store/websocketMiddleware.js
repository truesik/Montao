import SockJS from 'sockjs-client';
import * as actionType from '../constants/websocketConstants';
import {getMessage} from '../actions/MessageActions'
import {Stomp} from "stompjs/lib/stomp";

var sock;
var stomp;
var messageBoxSubscription;

export function websocketMiddleware(store) {
    return (next) => action => {
        if (action.type === actionType.CONNECT_TO_WEBSOCKET_REQUEST) {
            sock = new SockJS('/websocket');
            stomp = Stomp.over(sock);
            stomp.connect('guest', 'guest', () => {
                    store.dispatch({
                        type: actionType.CONNECT_TO_WEBSOCKET_SUCCESS
                    })
                },
                (error) => {
                    store.dispatch({
                        type: actionType.CONNECT_TO_WEBSOCKET_FAILURE,
                        payload: error
                    })
                });
        }
        if (stomp && action.type === actionType.SUBSCRIBE_TO_TOPIC_REQUEST) {
            messageBoxSubscription = stomp.subscribe(action.topic, (incoming) => {
                var message = JSON.parse(incoming.body);
                store.dispatch(getMessage(message));
            });
        }
        if (stomp && action.type === actionType.UNSUBSCRIBE_REQUEST) {
            if (messageBoxSubscription) {
                messageBoxSubscription.unsubscribe();
            }
        }
        return next(action);
    };
}
