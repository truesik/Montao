import React from 'react';
import { Link } from 'react-router';
import { Map } from 'immutable';

import checkExistence from '../../decorators/Checker';

import ChannelListDialog from '../ChannelListDialog';
import UserListDialog from '../UserListDialog';

@checkExistence
export default class Community extends React.Component {
  componentDidMount() {
    this.props.getUsers(this.props.params.community);
  }

  render() {
    const channels = this.props.community.get('channels').slice(0, 3);
    const channelsTemplate = channels.map(channel => (
      <li key={channel.id}>
        <Link to={`/community/${this.props.params.community}/channel/${channel.title}`}>{channel.title}</Link>
      </li>
    ));
    const subscribers = this.props.subscribers.slice(0, 3);
    const subscribersTemplate = subscribers.map(subscriber => (
      <li key={subscriber.getIn(['user', 'id'])}>{subscriber.getIn(['user', 'username'])}</li>
    ));
    return (
      <div>
        <ChannelListDialog communityTitle={this.props.params.community}
                           channels={this.props.community.get('channels')}
                           {...this.props.channelListDialog}
                           {...this.props.channelListDialogActions}/>
        <UserListDialog subscribers={this.props.subscribers}
                        {...this.props.userListDialog}
                        {...this.props.userListDialogActions}/>
        <div className="container" style={{ marginTop: 100 + 'px' }}>
          <div className="row">
            <div className="col-md-8">
              <div className="panel panel-default">
                <div className="panel-heading">Information</div>
                <div className="panel-body">
                  <dl className="dl-horizontal">
                    <dt>Title</dt>
                    <dd>{this.props.community.get('title')}</dd>
                    <dt>Description</dt>
                    <dd>{this.props.community.get('description')}</dd>
                  </dl>
                </div>
              </div>
            </div>
            <div className="offset-md-8 col-md-4">
              <div className="panel panel-default">
                <div className="panel-heading">Members
                  <a className="pull-right badge"
                     onClick={() => this.props.userListDialogActions.show()}>
                    {this.props.subscribers.length}
                  </a>
                </div>
                <div className="panel-body">
                  <ul className="nav nav-pills nav-justified">
                    {subscribersTemplate}
                  </ul>
                </div>
              </div>
              <div className="panel panel-default">
                <div className="panel-heading">Channels
                  <a className="pull-right badge"
                     onClick={() => this.props.channelListDialogActions.show()}>
                    {this.props.community.get('channels').length}
                  </a>
                </div>
                <div className="panel-body">
                  <ul className="nav nav-pills nav-justified">
                    {channelsTemplate}
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-md-12">
              <div className="panel panel-default">
                <div className="panel-heading">Latest posts</div>
                <div className="panel-body">

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Community.propTypes = {
  getUsers: React.PropTypes.func.isRequired,
  params: React.PropTypes.shape({
    community: React.PropTypes.string.isRequired
  }).isRequired,
  community: React.PropTypes.instanceOf(Map),
  subscribers: React.PropTypes.arrayOf(Map),
  channelListDialog: React.PropTypes.object.isRequired,
  channelListDialogActions: React.PropTypes.object.isRequired,
  userListDialog: React.PropTypes.object.isRequired,
  userListDialogActions: React.PropTypes.object.isRequired
};
