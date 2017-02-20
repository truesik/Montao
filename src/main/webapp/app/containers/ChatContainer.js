import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as websocketActions from '../actions/WebSocketActions';
import * as viewAction from '../actions/ViewActions';
import * as channelActions from '../actions/ChannelActions';
import * as communityActions from '../actions/CommunityActions';

import Chat from '../components/Chat';

const mapStateToProps = state => {
  return {
    isAuthorized: state.usersReducer.get('isAuthorized'),
    isSubscribed: state.communitiesReducer.get('isSubscribed'),
    addChannelDialog: {
      isShown: state.viewReducer.get('isShownAddChannelDialog')
    }
  };
};

const mapDispatchToProps = dispatch => {
  return {
    connectToWebSocket: bindActionCreators(websocketActions.connectToWebsocket, dispatch),
    disconnect: bindActionCreators(websocketActions.disconnect, dispatch),
    addChannelDialogActions: {
      show: bindActionCreators(viewAction.showAddChannelDialog, dispatch),
      hide: bindActionCreators(viewAction.hideAddChannelDialog, dispatch),
      addChannel: bindActionCreators(channelActions.add, dispatch)
    },
    checkSubscription: bindActionCreators(communityActions.checkSubscription, dispatch),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Chat);
