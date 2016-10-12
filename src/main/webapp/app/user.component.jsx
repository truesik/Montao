import * as React from "react";

export default class User extends React.Component {
    render() {
        var user = this.props.user;
        return (
            <li>
                <a href={`/${(user.user.username)}`}>{user.user.username}</a>
            </li>
        )
    }
}