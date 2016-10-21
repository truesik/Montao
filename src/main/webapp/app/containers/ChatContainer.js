import Chat from "../components/Chat";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {getLastOpenedChannel} from "../actions/ChannelActions";
import {sendMessage} from "../actions/MessageActions";

const mapStateToProps = state => {
    return {
        currentChannelTitle: state.channelsReducer.currentChannelTitle
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getLastOpenedChannel: bindActionCreators(getLastOpenedChannel, dispatch),
        sendMessage: bindActionCreators(sendMessage, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Chat)