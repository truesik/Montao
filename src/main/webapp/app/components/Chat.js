import * as React from "react";
import MessageBox from './MessageBox'
import MessageForm from './MessageForm'
import SideBar from './SideBar'
import {Stomp} from "stompjs/lib/stomp";
import SockJS from 'sockjs-client'
import $ from 'jquery'

export default class Chat extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentChannelTitle: '',
            stomp: null
        };
        this.getLastOpenedChannel = this.getLastOpenedChannel.bind(this);
        this.connectToWebSocket = this.connectToWebSocket.bind(this);
        this.handleWebSocketSuccessConnection = this.handleWebSocketSuccessConnection.bind(this);
        this.handleWebSocketFailureConnection = this.handleWebSocketFailureConnection.bind(this);
    }

    componentDidMount() {
        // Connect to websocket
        this.connectToWebSocket();
        this.getLastOpenedChannel();
        window.ee.addListener('Channel.changed', (channelTitle) => {
            this.setState({currentChannelTitle: channelTitle})
        })
    }

    componentWillUnmount() {
        window.ee.removeListener('Channel.changed')
    }

    getLastOpenedChannel() {
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: '/api/channel/get_last_opened_channel?communityTitle=' + $(location).attr('pathname').substring(1, $(location).attr('pathname').length),
            type: 'post',
            headers: headers,
            success: function (channel) {
                this.setState({
                    currentChannelTitle: channel.title
                })
            }.bind(this)
        });
    }

    connectToWebSocket() {
        var sock = new SockJS('/websocket');
        var stomp = Stomp.over(sock);
        stomp.connect(
            'guest',
            'guest',
            this.handleWebSocketSuccessConnection(stomp),
            this.handleWebSocketFailureConnection(stomp))
    }

    handleWebSocketSuccessConnection(stomp) {
        this.setState({
            stomp: stomp
        })
    }

    handleWebSocketFailureConnection(stomp) {

    }

    render() {
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-md-2 sidebar">
                        <SideBar/>
                    </div>
                    <div className="col-md-offset-2 col-md-10">
                        <MessageBox channel={this.state.currentChannelTitle} webSocket={this.state.stomp}/>
                        <MessageForm/>
                    </div>
                </div>
            </div>
        )
    }
}