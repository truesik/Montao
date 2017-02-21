import React from 'react';
import ReactTransitionGroup from 'react-addons-transition-group';
import { Map } from 'immutable';

import Message from './Message';

export default class MessageBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      scrollHeight: 0
    };
  }

  componentDidMount() {
    this.node.addEventListener('scroll', this.handleScrollEvent(this.node));

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

  componentWillUnmount() {
    this.node.removeEventListener('scroll', this.handleScrollEvent(this.node));
  }

  handleScrollEvent = (node) => {
    // When scroll top equal 0
    if (node.scrollTop == 0) {
      // Store current scroll height
      this.setState({ scrollHeight: node.scrollHeight });

      let startRowPosition = this.props.startRowPosition;
      // Get oldest messages
      this.props.getOldestMessages(this.props.community, this.props.channel, startRowPosition);
    }
  };

  render() {
    const messages = this.props.messages;
    const messagesTemplate = messages.map(message => (
      <Message key={message.get('id')} message={message}/>
    ));
    return (
      <div className="chat" ref={node => this.node = node}>
        {messages.length > 0 &&
        <ReactTransitionGroup>
          {messagesTemplate}
        </ReactTransitionGroup>}
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
