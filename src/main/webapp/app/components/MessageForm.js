import React from "react";
import ReactDOM from "react-dom";

export default class MessageForm extends React.Component {
    handleMessageSubmit(event) {
        if ((event.keyCode == 10 || event.keyCode == 13) && event.ctrlKey) {
            var messageInput = ReactDOM.findDOMNode(this.refs.messageInput);
            var currentCommunityTitle = this.props.path.currentCommunityTitle;
            var currentChannelTitle = this.props.path.currentChannelTitle;
            if (messageInput.value.trim().length > 0) {
                var onSubmit = this.props.onSubmit;
                onSubmit(currentCommunityTitle, currentChannelTitle, messageInput.value);
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
                       onKeyDown={this.handleMessageSubmit.bind(this)}/>
            </div>
        )
    }
}