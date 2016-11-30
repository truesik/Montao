import Chat from "../components/Chat";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {getLastOpenedChannel} from "../actions/ChannelActions";
import {sendMessage} from "../actions/MessageActions";
import {connectToWebsocket, subscribeToTopic, unsubscribe, disconnect} from "../actions/WebSocketActions";
import * as viewAction from "../actions/ViewActions";
import * as channelActions from "../actions/ChannelActions";

const mapStateToProps = state => {
    return {
        currentChannelTitle: state.channelsReducer.currentChannelTitle,
        isConnected: state.websocketReducer.isConnected,
        isAuthorized: state.usersReducer.isAuthorized,
        addChannelDialog: {
            isShown: state.viewReducer.isShownAddChannelDialog
        }
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getLastOpenedChannel: bindActionCreators(getLastOpenedChannel, dispatch),
        sendMessage: bindActionCreators(sendMessage, dispatch),
        connectToWebSocket: bindActionCreators(connectToWebsocket, dispatch),
        subscribeToTopic: bindActionCreators(subscribeToTopic, dispatch),
        unsubscribe: bindActionCreators(unsubscribe, dispatch),
        disconnect: bindActionCreators(disconnect, dispatch),
        addChannelDialogActions: {
            show: bindActionCreators(viewAction.showAddChannelDialog, dispatch),
            hide: bindActionCreators(viewAction.hideAddChannelDialog, dispatch),
            addChannel: bindActionCreators(channelActions.add, dispatch)
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Chat)