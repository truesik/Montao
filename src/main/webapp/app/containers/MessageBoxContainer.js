import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import MessageBox from "../components/MessageBox";
import * as messageActions from '../actions/MessageActions'

const mapStateToProps = (state) => {
    return {
        messages: state.messagesReducer.messages,
        error: state.messagesReducer.error
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        getMessages: bindActionCreators(messageActions.getMessages, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(MessageBox)