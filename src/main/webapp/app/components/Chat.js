import * as React from "react";
import MessageBox from './MessageBox'
import MessageForm from './MessageForm'
import SideBar from './SideBar'
import SockJS from 'sockjs-client'
import $ from 'jquery'
import {Stomp} from "stompjs/lib/stomp";

export default class Chat extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentChannelTitle: '',
            stomp: null
        };
        this.getLastOpenedChannel = this.getLastOpenedChannel.bind(this);
        this.connectToWebSocket = this.connectToWebSocket.bind(this);
    }

    componentDidMount() {
        this.getLastOpenedChannel();
        // Connect to websocket
        this.connectToWebSocket();
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
            () => {
                this.setState({
                    stomp: stomp
                })
            },
            (error) => {
                console.log('STOMP: ' + error);
                setTimeout(this.connectToWebSocket, 10000);
                console.log('STOMP: Reconnecting in 10 seconds');
            }
        )
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