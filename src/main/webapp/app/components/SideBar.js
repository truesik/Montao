import * as React from "react";
import ChannelListContainer from '../containers/ChannelListContainer'
import UserListContainer from '../containers/UserListContainer'

export default class SideBar extends React.Component {
    render() {
        return (
            <div>
                <ChannelListContainer/>
                <UserListContainer/>
            </div>
        )
    }
}