import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as channelActions from '../actions/ChannelActions';

import ChannelList from '../components/ChannelList';

const mapStateToProps = state => {
  return {
    channels: state.channelsReducer.get('channels').toArray()
  };
};

const mapDispatchToProps = dispatch => {
  return {
    getChannels: bindActionCreators(channelActions.getChannels, dispatch),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ChannelList);
