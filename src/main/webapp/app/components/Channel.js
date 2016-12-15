import * as React from "react";

export const Channel = ({channel, onClick}) => {
    return (
        <li>
            <a onClick={() => onClick(channel.get('title'))}>{channel.get('title')}</a>
        </li>
    )
};

export default Channel;
