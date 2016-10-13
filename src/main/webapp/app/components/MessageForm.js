import * as React from "react";

export default class MessageForm extends React.Component {
    render() {
        return (
            <form className="message-input">
                <input type="text" className="form-control"/>
            </form>
        )
    }
}