import * as React from "react";

export default class Channel extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(event) {
        event.preventDefault();
        window.ee.emit('Channel.changed', this.props.channel.title)
    }

    render() {
        var channel = this.props.channel;
        var path = $(location).attr('pathname');
        return (
            <li>
                <a
                    href={`${(path)}/channels/${(channel.title)}`}
                    onClick={this.handleClick}
                >{channel.title}</a>
            </li>
        )
    }
}