import * as React from "react";

export default class Channel extends React.Component {
    handleClick(event) {
        event.preventDefault();
        console.log("someday... someday")
    }

    render() {
        var channel = this.props.channel;
        var path = $(location).attr('pathname');
        return (
            <li>
                <a
                    href={`${(path)}/channels/${(channel.title)}`}
                    onClick={::this.handleClick}
                >{channel.title}</a>
            </li>
        )
    }
}