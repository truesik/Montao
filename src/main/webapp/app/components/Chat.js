import * as React from "react";
import MessageBoxContainer from '../containers/MessageBoxContainer'
import MessageForm from './MessageForm'
import SideBar from './SideBar'

export default class Chat extends React.Component {
    componentDidMount() {
        var lastOpenedChannel = this.props.getLastOpenedChannel;
        lastOpenedChannel();
    }

    render() {
        const currentCommunityTitle = this.props.currentCommunityTitle;
        const currentChannelTitle = this.props.currentChannelTitle;
        const sendMessage = this.props.sendMessage;
        const path = {
            currentCommunityTitle: currentCommunityTitle,
            currentChannelTitle: currentChannelTitle
        };
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-md-2 sidebar">
                        <SideBar/>
                    </div>
                    <div className="col-md-offset-2 col-md-10">
                        <MessageBoxContainer channel={currentChannelTitle}/>
                        <MessageForm onSubmit={sendMessage} path={path}/>
                    </div>
                </div>
            </div>
        )
    }
}