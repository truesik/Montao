import * as React from "react";

export const Channel = ({channel, onClick}) => {
    return (
        <li>
            <a onClick={() => onClick(channel.title)}>{channel.title}</a>
        </li>
    )
};

export default Channel;
