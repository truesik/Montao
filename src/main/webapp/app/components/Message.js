import * as React from "react";
import fadeUp from "../decorators/FadeUp";

@fadeUp
export default class Message extends React.Component {
    render() {
        const message = this.props.message;
        return (
            <div id={message.get('id')} className={this.props.className}>
                <div className="panel-body">
                    <strong>{message.getIn(['user', 'username'])}</strong>
                    <small>{message.get('receivedTime')}</small>
                    <p>{message.get('text')}</p>
                </div>
            </div>
        )
    }
}