import * as React from "react";
import Channel from './Channel'

export default class ChannelList extends React.Component {
    componentDidMount() {
        const getChannels = this.props.getChannels;
        getChannels();
    }

    render() {
        const channels = this.props.channels;
        const channelListTemplate = channels.map(channel => {
            return (
                <Channel key={channel.id} channel={channel}/>
            )
        });
        return (
            <div>
                <span>Channels </span>
                <ul className="nav nav-sidebar" id="channelList">
                    {channelListTemplate}
                </ul>
            </div>
        )
    }
}