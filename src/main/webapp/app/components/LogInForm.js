import React from "react";
import ReactDOM from "react-dom";

export default class LogInForm extends React.Component {
    componentDidMount() {
        var node = ReactDOM.findDOMNode(this);
        $(node).on('hidden.bs.modal', () => {
            this.props.hide();
        })
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isShown !== nextProps.isShown) {
            if (nextProps.isShown) {
                ::this.showModal();
            } else {
                ::this.hideModal();
            }
        }
    }

    showModal() {
        var node = ReactDOM.findDOMNode(this);
        $(node).modal('show');
    }

    hideModal() {
        var node = ReactDOM.findDOMNode(this);
        $(node).modal('hide');
    }

    handleLogIn() {
        let username = ReactDOM.findDOMNode(this.refs.username).val;
        let password = ReactDOM.findDOMNode(this.refs.password).val;
        let userCredentials = {
            username: username,
            password: password
        };
        this.props.logIn(userCredentials);
    }

    render() {
        return (
            <div className="modal fade"
                 tabIndex="-1"
                 role="dialog"
                 aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <button type="button" className="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span className="sr-only">Close</span>
                            </button>
                            <h4 className="modal-title" id="myModalLabel">Log In</h4>
                        </div>
                        <div className="modal-body">
                            <form method="post">
                                <div className="form-group">
                                    <label htmlFor="username">Username</label>
                                    <input type="text"
                                           className="form-control"
                                           name="username"
                                           placeholder="Username"
                                           ref="username"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="password">Password</label>
                                    <input type="password"
                                           className="form-control"
                                           name="password"
                                           placeholder="Password"
                                           ref="password"/>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit"
                                    className="btn btn-primary"
                                    onClick={() => ::this.handleLogIn()}>
                                Log In
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}