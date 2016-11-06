import * as React from "react";
import Channel from './Channel'

export default class ChannelList extends React.Component {
    componentDidMount() {
        const getChannels = this.props.getChannels;
        getChannels(this.props.communityTitle);
    }

    render() {
        const channels = this.props.channels;
        var setCurrentChannel = this.props.setCurrentChannel;
        const channelListTemplate = channels.map(channel => {
            return (
                <Channel key={channel.id} channel={channel} onClick={setCurrentChannel}/>
            )
        });
        return (
            <div>
                <ul className="nav nav-sidebar" id="channelList">
                    {channelListTemplate}
                </ul>
            </div>
        )
    }
}