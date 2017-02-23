import React from 'react';

import './MessageForm.scss';

export default class MessageForm extends React.Component {
  handleMessageSubmit = (event) => {
    // Check Ctrl + ENTER
    if ((event.keyCode == 10 || event.keyCode == 13) && event.ctrlKey) {
      // Check message input value not empty
      if (this.messageInput.value.trim().length > 0) {
        // Send message
        this.props.onSubmit(this.props.community, this.props.channel, this.messageInput.value);
        // Clean up input value
        this.messageInput.value = '';
      }
    }
  };

  render() {
    return (
      <div className="message-input">
        <input type="text"
               className="form-control"
               ref={node => this.messageInput = node}
               onKeyDown={this.handleMessageSubmit}
               disabled={this.props.disabled}/>
      </div>
    );
  }
}

MessageForm.propTypes = {
  onSubmit: React.PropTypes.func.isRequired,
  community: React.PropTypes.string.isRequired,
  channel: React.PropTypes.string.isRequired,
  disabled: React.PropTypes.bool.isRequired
};
