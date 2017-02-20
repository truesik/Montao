import React from 'react';
import { Link } from 'react-router';

import setModal from '../decorators/Modal';

@setModal
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
      <div className="modal fade bs-example-modal-sm" id="myModal" tabIndex="-1" role="dialog"
           aria-labelledby="myModalLabel"
           aria-hidden="true">
        <div className="modal-dialog modal-sm">
          <div className="modal-content" style={{ overflow: 'auto', paddingBottom: '15px' }}>
            <div className="modal-header">
              <button type="button" className="close" data-dismiss="modal">
                <span aria-hidden="true">&times;</span>
                <span className="sr-only">Close</span>
              </button>
              <h4 className="modal-title" id="myModalLabel">Channel List</h4>
            </div>
            <div className="list-group">
              {channelsTemplate}
            </div>
          </div>
        </div>
      </div>
    );
  }
}
