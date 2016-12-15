import * as channelActions from "../actions/ChannelActions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import ChannelList from "../components/ChannelList"

const mapStateToProps = state => {
    return {
        channels: state.channelsReducer.get('channels').toArray()
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getChannels: bindActionCreators(channelActions.getChannels, dispatch),
        setCurrentChannel: bindActionCreators(channelActions.setCurrentChannel, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ChannelList)