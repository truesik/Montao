import * as React from "react";
import ChannelListContainer from '../containers/ChannelListContainer'
import UserListContainer from '../containers/UserListContainer'

export default class SideBar extends React.Component {
    render() {
        return (
            <div>
                <div>
                    <span>Channels </span>
                    <a className="glyphicon glyphicon-plus pull-right"
                       onClick={() => this.props.showAddChannelDialog()}></a>
                    <ChannelListContainer communityTitle={this.props.communityTitle}/>
                </div>
                <div>
                    <span>Users</span>
                    <UserListContainer communityTitle={this.props.communityTitle}/>
                </div>
            </div>
        )
    }
}