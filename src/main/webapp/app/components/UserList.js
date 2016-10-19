import * as React from "react";
import User from './User'
import {connect} from "react-redux";
import {getUsers} from "../actions/UserActions";
import {bindActionCreators} from 'redux';

class UserList extends React.Component {
    componentDidMount() {
        const getUsers = this.props.getUsers;
        getUsers();
    }

    render() {
        const subscribers = this.props.subscribers;
        const userListTemplate = subscribers.map(subscriber => {
            return (
                <User key={subscriber.user.id} user={subscriber.user}/>
            )
        });
        return (
            <div>
                <span>Users</span>
                <ul className="nav nav-sidebar" id="userList">
                    {userListTemplate}
                </ul>
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        subscribers: state.usersReducer.subscribers,
        error: state.usersReducer.error
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getUsers: bindActionCreators(getUsers, dispatch)
    }

};

export default connect(mapStateToProps, mapDispatchToProps)(UserList);