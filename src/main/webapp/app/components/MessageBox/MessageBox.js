import React from 'react';
import { Map } from 'immutable';

import Message from '../Message';

import './MessageBox.scss';

export default class MessageBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      scrollHeight: 0
    };
  }

  componentDidMount() {
    this.props.getMessages(this.props.community, this.props.channel, 0);
  }

  componentWillReceiveProps(nextProps) {
    // When channel changed
    if (this.props.channel !== nextProps.channel) {
      // Get messages
      this.props.getMessages(this.props.community, nextProps.channel, 0);
    }
  }

  componentDidUpdate() {
    if (this.props.scrollBottom) {
      // Scroll to bottom
      this.node.scrollTop = this.node.scrollHeight;
    } else {
      // Stay on position
      this.node.scrollTop = this.node.scrollHeight - this.state.scrollHeight;
    }
  }

  handleScrollEvent = () => {
    // When scroll top equal 0
    if (this.node.scrollTop == 0) {
      // Store current scroll height
      this.setState({ scrollHeight: this.node.scrollHeight });

      // Get oldest messages
      this.props.getOldestMessages(this.props.community, this.props.channel, this.props.startRowPosition);
    }
  };

  render() {
    const messages = this.props.messages;
    const messagesTemplate = messages.map(message => (
      <Message key={message.get('id')} message={message}/>
    ));
    return (
      <div className="chat" ref={node => this.node = node} onScroll={this.handleScrollEvent}>
        {messagesTemplate}
      </div>
    );
  }
}

MessageBox.propTypes = {
  getMessages: React.PropTypes.func.isRequired,
  community: React.PropTypes.string,
  channel: React.PropTypes.string,
  startRowPosition: React.PropTypes.number.isRequired,
  getOldestMessages: React.PropTypes.func.isRequired,
  scrollBottom: React.PropTypes.bool.isRequired,
  messages: React.PropTypes.arrayOf(Map)
};
