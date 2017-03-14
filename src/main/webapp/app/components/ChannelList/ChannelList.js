import React from 'react';
import { Link } from 'react-router';
import { Map } from 'immutable';

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
    );
  }
}

ChannelList.propTypes = {
  getChannels: React.PropTypes.func.isRequired,
  communityTitle: React.PropTypes.string.isRequired,
  channels: React.PropTypes.arrayOf(Map)
};
