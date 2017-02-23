import React from 'react';
import { Link } from 'react-router';

import setModal from '../../decorators/Modal';

@setModal('Channel List', true)
export default class ChannelListDialog extends React.Component {
  render() {
    const channelsTemplate = this.props.channels.map(channel => (
      <Link to={`/community/${this.props.communityTitle}/channel/${channel.title}`}
            className="list-group-item"
            key={channel.id}>
        {channel.title}
      </Link>
    ));
    return (
      <div className="list-group">
        {channelsTemplate}
      </div>
    );
  }
}

ChannelListDialog.propTypes = {
  channels: React.PropTypes.array,
  communityTitle: React.PropTypes.string.isRequired
};
