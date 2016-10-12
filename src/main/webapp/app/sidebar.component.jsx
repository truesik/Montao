import * as React from "react";
import ChannelList from './channellist.component'
import UserList from './userlist.component'

export default class SideBar extends React.Component {
    render() {
        return (
            <div>
                <ChannelList/>
                <UserList/>
            </div>
        )
    }
}