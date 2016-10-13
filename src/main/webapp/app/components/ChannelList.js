import * as React from "react";
import Channel from './Channel'
import $ from 'jquery'

export default class ChannelList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            channels: []
        };
        this.getChannels = this.getChannels.bind(this);
    }

    componentDidMount() {
        this.getChannels();
    }

    getChannels() {
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: '/api/channel/get_channels?communityTitle=' + $(location).attr('pathname').substring(1, $(location).attr('pathname').length),
            type: 'post',
            headers: headers,
            success: function (result) {
                this.setState({
                    channels: result
                })
            }.bind(this)
        });
    }

    render() {
        var channels = this.state.channels;
        var channelListTemplate = channels.map(function (channel, index) {
            return (
                <Channel key={channel.id} channel={channel}/>
            )
        });
        return (
            <div>
                <span>Channels </span>
                <ul className="nav nav-sidebar" id="channelList">
                    {channelListTemplate}
                </ul>
            </div>
        )
    }
}