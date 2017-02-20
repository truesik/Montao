import * as React from 'react';

import MessageBoxContainer from '../containers/MessageBoxContainer';
import MessageForm from './MessageForm';

export default class Channel extends React.Component {
  componentWillReceiveProps(nextProps) {
    if (this.props.isConnected !== nextProps.isConnected && nextProps.isConnected) {
      this.props.subscribeToTopic(this.props.params.community, nextProps.params.channel);
    }
    if (this.props.params.channel !== nextProps.params.channel && nextProps.isConnected) {
      this.props.unsubscribe();
      this.props.subscribeToTopic(this.props.params.community, nextProps.params.channel);
    }
  }

  componentWillUnmount() {
    this.props.unsubscribe();
  }

  render() {
    return (
      <div>
        <MessageBoxContainer {...this.props.params}/>
        <MessageForm onSubmit={this.props.sendMessage}
                     {...this.props.params}
                     disabled={!this.props.isAuthorized}/>
      </div>
    );
  }
}
