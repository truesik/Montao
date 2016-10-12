import * as React from "react";
import * as ReactDOM from "react/lib/ReactDOM";
import Message from './message.component'
import $ from 'jquery'
import {Stomp} from 'stompjs'

export default class MessageBox extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            startRowPosition: 0,
            messages: []
        };
        this.subscribeToTopic = this.subscribeToTopic.bind(this);
        this.newMessageHandler = this.subscribeToTopic.bind(this);
        this.handleScroll = this.handleScroll.bind(this);
        this.getMessages = this.getMessages.bind(this);
    }

    componentDidMount() {
        // Set event listener to message box scroll
        var node = ReactDOM.findDOMNode(this);
        node.addEventListener('scroll', this.handleScroll)
    }

    componentWillUnmount() {
        this.removeEventListener('scroll', this.handleScroll);
        if (this.props.webSocket != null) {
            this.props.webSocket.unsubscribe();
        }
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.channel !== nextProps.channel) {
            console.log('balafdgsfgdf');
            // First messages load
            this.getMessages(nextProps.channel);
            var node = ReactDOM.findDOMNode(this);
            node.scrollTop = node.scrollHeight;
        }
        if (this.props.webSocket !== nextProps.webSocket) {
            // Subscribe to websocket topic
            this.subscribeToTopic(nextProps.webSocket);
        }
    }

    subscribeToTopic(websocket) {
        var communityTitle = $(location).attr('pathname').substring(1, $(location).attr('pathname').length);
        websocket.subscribe('/topic/' + communityTitle + '/' + this.props.channel, this.newMessageHandler)
    }

    newMessageHandler(incoming) {
        var message = JSON.parse(incoming.body);
        var nextMessage = message.append(this.state.messages);
        this.setState({
            messages: nextMessage
        })
    }

    handleScroll(event) {
    }

    getMessages(channelTitle) {
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: '/api/message/get_messages?communityTitle=' + $(location).attr('pathname').substring(1, $(location).attr('pathname').length) + '&channelTitle=' + channelTitle + '&startRowPosition=' + this.state.startRowPosition,
            type: 'post',
            headers: headers,
            success: function (result) {
                this.setState({
                    messages: result,
                    startRowPosition: 40
                })
            }.bind(this)
        });
    }

    render() {
        var messages = this.state.messages;
        var messagesTemplate = messages.map(function (message) {
            return (
                <Message key={message.id} message={message}/>
            )
        });
        return (
            <div className="chat" id="messages">

            </div>
        )
    }
}