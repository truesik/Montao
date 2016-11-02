import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import MessageBox from "../components/MessageBox";
import * as messageActions from '../actions/MessageActions'

const mapStateToProps = (state) => {
    return {
        messages: state.messagesReducer.messages,
        error: state.messagesReducer.error,
        scrollBottom: state.messagesReducer.scrollBottom,
        startRowPosition: state.messagesReducer.startRowPosition
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        getMessages: bindActionCreators(messageActions.getMessages, dispatch),
        getOldestMessages: bindActionCreators(messageActions.getOldestMessages, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(MessageBox)