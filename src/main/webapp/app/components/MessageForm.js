import React from "react";
import ReactDOM from "react-dom";

export default class MessageForm extends React.Component {
    handleMessageSubmit(event) {
        // Check Ctrl + ENTER
        if ((event.keyCode == 10 || event.keyCode == 13) && event.ctrlKey) {
            var messageInput = ReactDOM.findDOMNode(this.refs.messageInput);
            var currentCommunityTitle = this.props.path.currentCommunityTitle;
            var currentChannelTitle = this.props.path.currentChannelTitle;
            // Check message input value not empty
            if (messageInput.value.trim().length > 0) {
                var onSubmit = this.props.onSubmit;
                // Send message
                onSubmit(currentCommunityTitle, currentChannelTitle, messageInput.value);
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
        )
    }
}