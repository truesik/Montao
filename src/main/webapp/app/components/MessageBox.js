import * as React from "react";
import Message from './Message'
import * as ReactDOM from "react/lib/ReactDOM";

export default class MessageBox extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            scrollHeight: 0
        }
    }
    componentDidMount() {
        var node = ReactDOM.findDOMNode(this);
        node.addEventListener('scroll', () => {
            // When scroll top equal 0
            if (node.scrollTop == 0) {
                // Store current scroll height
                this.state.scrollHeight = node.scrollHeight;
                let currentCommunityTitle = $(location).attr('pathname').substring(1);
                let channelTitle = this.props.channel;
                let startRowPosition = this.props.startRowPosition;
                // Get oldest messages
                this.props.getOldestMessages(currentCommunityTitle, channelTitle, startRowPosition)
            }
        })
    }
    componentWillReceiveProps(nextProps) {
        // When channel changed
        if (this.props.channel !== nextProps.channel) {
            var currentCommunityTitle = $(location).attr('pathname').substring(1);
            // Get messages
            this.props.getMessages(currentCommunityTitle, nextProps.channel, 0)
        }
    }

    componentDidUpdate() {
        var node = ReactDOM.findDOMNode(this);
        if (this.props.scrollBottom) {
            // Scroll to bottom
            node.scrollTop = node.scrollHeight;
        } else {
            // Stay on position
            node.scrollTop = node.scrollHeight - this.state.scrollHeight;
        }
    }

    render() {
        var messages = this.props.messages;
        var messagesTemplate = messages.map(message => {
            return (
                <Message key={message.id} message={message}/>
            )
        });
        return (
            <div className="chat" id="messages">
                {messagesTemplate}
            </div>
        )
    }
}