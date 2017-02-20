import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as websocketActions from '../actions/WebSocketActions';
import * as messageActions from '../actions/MessageActions';

import Channel from '../components/Channel';

const mapStateToProps = (state) => {
  return {
    isConnected: state.websocketReducer.get('isConnected'),
    isAuthorized: state.usersReducer.get('isAuthorized')
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    subscribeToTopic: bindActionCreators(websocketActions.subscribeToTopic, dispatch),
    unsubscribe: bindActionCreators(websocketActions.unsubscribe, dispatch),
    sendMessage: bindActionCreators(messageActions.sendMessage, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Channel);
