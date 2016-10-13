import * as React from "react";
import ChannelList from './ChannelList'
import UserList from './UserList'

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