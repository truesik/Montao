import * as React from "react";

export const Channel = ({channel, onClick}) => (
    <li>
        <a onClick={() => onClick(channel)}>{channel.get('title')}</a>
    </li>
);

export default Channel;
