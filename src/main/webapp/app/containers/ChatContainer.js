import Chat from "../components/Chat";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {getLastOpenedChannel} from "../actions/ChannelActions";
import {sendMessage} from "../actions/MessageActions";
import {connectToWebsocket, subscribeToTopic, unsubscribe, disconnect} from "../actions/WebSocketActions";
import * as viewAction from "../actions/ViewActions";
import * as channelActions from "../actions/ChannelActions";
import * as communityActions from "../actions/CommunityActions";

const mapStateToProps = state => {
    return {
        currentChannelTitle: state.channelsReducer.get('currentChannelTitle'),
        isConnected: state.websocketReducer.isConnected,
        isAuthorized: state.usersReducer.isAuthorized,
        isSubscribed: state.communitiesReducer.get('isSubscribed'),
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
        },
        checkSubscription: bindActionCreators(communityActions.checkSubscription, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Chat)