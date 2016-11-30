import React from "react";
import MessageBoxContainer from '../containers/MessageBoxContainer';
import MessageForm from './MessageForm';
import SideBar from './SideBar';
import AddChannelDialog from "./AddChannelDialog";

export default class Chat extends React.Component {
    componentDidMount() {
        this.props.connectToWebSocket();
    }

    componentWillUnmount() {
        this.props.unsubscribe();
        this.props.disconnect();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isConnected !== nextProps.isConnected && nextProps.isConnected) {
            this.props.getLastOpenedChannel(this.props.params.community);
        }
        if (this.props.currentChannelTitle != nextProps.currentChannelTitle && this.props.isConnected) {
            this.props.unsubscribe();
            this.props.subscribeToTopic(this.props.params.community, nextProps.currentChannelTitle);
        }
    }

    render() {
        const currentCommunityTitle = this.props.params.community;
        const currentChannelTitle = this.props.currentChannelTitle;
        const sendMessage = this.props.sendMessage;
        const path = {
            currentCommunityTitle: currentCommunityTitle,
            currentChannelTitle: currentChannelTitle
        };
        return (
            <div>
                <AddChannelDialog {...this.props.addChannelDialog}
                                  {...this.props.addChannelDialogActions}
                                  communityTitle={currentCommunityTitle}/>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-2 sidebar">
                            <SideBar communityTitle={currentCommunityTitle}
                                     showAddChannelDialog={this.props.addChannelDialogActions.show}/>
                        </div>
                        <div className="col-md-offset-2 col-md-10">
                            <MessageBoxContainer channel={currentChannelTitle} path={path}/>
                            <MessageForm onSubmit={sendMessage} path={path} disabled={!this.props.isAuthorized}/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}