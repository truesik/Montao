import * as React from "react";
import fadeUp from "../decorators/FadeUp";

@fadeUp
export default class Message extends React.Component {
    render() {
        var message = this.props.message;
        return (
            <div id={message.id} className={this.props.className}>
                <div className="panel-body">
                    <strong>{message.user.username}</strong>
                    <small>{message.receivedTime}</small>
                    <p>{message.message}</p>
                </div>
            </div>
        )
    }
}