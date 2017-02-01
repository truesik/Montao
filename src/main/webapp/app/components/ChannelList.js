import * as React from 'react';
import { Link } from 'react-router';

export default class ChannelList extends React.Component {
    componentDidMount() {
        const getChannels = this.props.getChannels;
        getChannels(this.props.communityTitle);
    }

    render() {
        const channels = this.props.channels;
        const channelListTemplate = channels.map(channel => (
            <li key={channel.get('id')}>
                <Link to={`/community/${this.props.communityTitle}/channel/${channel.get('title')}`}>
                    {channel.get('title')}
                </Link>
            </li>
        ));
        return (
            <div>
                <ul className="nav nav-sidebar" id="channelList">
                    {channelListTemplate}
                </ul>
            </div>
        )
    }
}