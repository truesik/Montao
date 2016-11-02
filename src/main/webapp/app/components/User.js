import React from "react";

const User = ({user}) => {
    return (
        <li>
            <a href={`/${(user.username)}`}>{user.username}</a>
        </li>
    )
};

export default User
