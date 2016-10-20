import {getChannels} from "../actions/ChannelActions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import ChannelList from "../components/ChannelList"

const mapStateToProps = state => {
    return {
        channels: state.channelsReducer.channels
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getChannels: bindActionCreators(getChannels, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ChannelList)