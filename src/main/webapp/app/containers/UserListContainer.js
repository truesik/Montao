import {getUsers} from "../actions/UserActions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import UserList from "../components/UserList";

const mapStateToProps = state => {
    return {
        subscribers: state.usersReducer.subscribers,
        error: state.usersReducer.error,
        channelListFetching: state.usersReducer.channelListFetching
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getUsers: bindActionCreators(getUsers, dispatch)
    }

};

export default connect(mapStateToProps, mapDispatchToProps)(UserList);