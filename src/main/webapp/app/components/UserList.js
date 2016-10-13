import * as React from "react";
import User from './User'
import $ from 'jquery'

export default class UserList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: []
        };
        this.getSubscribedUsers = this.getSubscribedUsers.bind(this);
    }

    componentDidMount() {
        this.getSubscribedUsers()
    }

    getSubscribedUsers() {
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: '/api/user/get_subscribed_users?communityTitle=' + $(location).attr('pathname').substring(1, $(location).attr('pathname').length),
            type: 'post',
            headers: headers,
            success: function (result) {
                this.setState({
                    users: result
                })
            }.bind(this)
        });
    }

    render() {
        var users = this.state.users;
        var userListTemplate = users.map(function (user, index) {
            return (
                <User key={user.id} user={user}/>
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