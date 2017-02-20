import React from 'react';
import ReactDOM from 'react-dom';

export default class MessageForm extends React.Component {
  handleMessageSubmit(event) {
    // Check Ctrl + ENTER
    if ((event.keyCode == 10 || event.keyCode == 13) && event.ctrlKey) {
      const messageInput = ReactDOM.findDOMNode(this.refs.messageInput);
      // Check message input value not empty
      if (messageInput.value.trim().length > 0) {
        // Send message
        this.props.onSubmit(this.props.community, this.props.channel, messageInput.value);
        // Clean up input value
        messageInput.value = '';
      }
    }
  }

  render() {
    return (
      <div className="message-input">
        <input type="text"
               className="form-control"
               ref="messageInput"
               onKeyDown={this.handleMessageSubmit.bind(this)}
               disabled={this.props.disabled}/>
      </div>
    );
  }
}
