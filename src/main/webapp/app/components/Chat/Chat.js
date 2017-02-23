import React from 'react';

import AddChannelDialog from '../AddChannelDialog';
import SideBar from '../SideBar';

import './Chat.scss';

export default class Chat extends React.Component {
  componentDidMount() {
    // Check user subscription on this community
    this.props.checkSubscription(this.props.params.community);
    // Connect to web socket
    this.props.connectToWebSocket();
  }

  componentWillUnmount() {
    this.props.disconnect();
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.isAuthorized !== nextProps.isAuthorized) {
      this.props.checkSubscription(this.props.params.community);
    }
  }

  render() {
    return (
      <div>
        <AddChannelDialog {...this.props.addChannelDialog}
                          {...this.props.addChannelDialogActions}
                          communityTitle={this.props.params.community}/>
        <div className="container-fluid">
          <div className="row">
            <div className="col-md-2 sidebar" name="sidebar">
              <SideBar communityTitle={this.props.params.community}
                       showAddChannelDialog={this.props.addChannelDialogActions.show}
                       isSubscribed={this.props.isSubscribed}/>
            </div>
            <div className="col-md-offset-2 col-md-10" name="channel">
              {this.props.children}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Chat.propTypes = {
  checkSubscription: React.PropTypes.func.isRequired,
  params: React.PropTypes.shape({
    community: React.PropTypes.string.isRequired
  }).isRequired,
  connectToWebSocket: React.PropTypes.func.isRequired,
  disconnect: React.PropTypes.func.isRequired,
  isAuthorized: React.PropTypes.bool.isRequired,
  addChannelDialog: React.PropTypes.object.isRequired,
  addChannelDialogActions: React.PropTypes.object.isRequired,
  isSubscribed: React.PropTypes.bool.isRequired,
  children: React.PropTypes.element.isRequired
};
