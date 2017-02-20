import React from 'react';
import ReactDOM from 'react-dom';
import ReactTransitionGroup from 'react-addons-transition-group';

import Message from './Message';

export default class MessageBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      scrollHeight: 0
    };
  }

  componentDidMount() {
    const node = ReactDOM.findDOMNode(this);
    node.addEventListener('scroll', this.handleScrollEvent.bind(this, node));

    this.props.getMessages(this.props.community, this.props.channel, 0);
  }

  componentWillUnmount() {
    const node = ReactDOM.findDOMNode(this);
    node.removeEventListener('scroll', this.handleScrollEvent.bind(this, node));
  }

  handleScrollEvent(node) {
    // When scroll top equal 0
    if (node.scrollTop == 0) {
      // Store current scroll height
      this.state.scrollHeight = node.scrollHeight;

      let startRowPosition = this.props.startRowPosition;
      // Get oldest messages
      this.props.getOldestMessages(this.props.community, this.props.channel, startRowPosition);
    }
  }

  componentWillReceiveProps(nextProps) {
    // When channel changed
    if (this.props.channel !== nextProps.channel) {
      // Get messages
      this.props.getMessages(this.props.community, nextProps.channel, 0);
    }
  }

  componentDidUpdate() {
    const node = ReactDOM.findDOMNode(this);
    if (this.props.scrollBottom) {
      // Scroll to bottom
      node.scrollTop = node.scrollHeight;
    } else {
      // Stay on position
      node.scrollTop = node.scrollHeight - this.state.scrollHeight;
    }
  }

  render() {
    const messages = this.props.messages;
    const messagesTemplate = messages.map(message => (
      <Message key={message.get('id')} message={message}/>
    ));
    return (
      <div className="chat">
        {messages.length > 0 &&
        <ReactTransitionGroup>
          {messagesTemplate}
        </ReactTransitionGroup>}
      </div>
    );
  }
}
