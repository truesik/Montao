import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as messageActions from '../actions/MessageActions';

import MessageBox from '../components/MessageBox';

const mapStateToProps = (state) => {
  return {
    messages: state.messagesReducer.get('messages').toArray(),
    error: state.messagesReducer.get('error'),
    scrollBottom: state.messagesReducer.get('scrollBottom'),
    startRowPosition: state.messagesReducer.get('startRowPosition')
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getMessages: bindActionCreators(messageActions.getMessages, dispatch),
    getOldestMessages: bindActionCreators(messageActions.getOldestMessages, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(MessageBox);
