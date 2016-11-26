import React from "react";
import ReactDOM from "react-dom";

export default class SignUpDialog extends React.Component {
    componentDidMount() {
        const node = ReactDOM.findDOMNode(this);
        $(node).on('hidden.bs.modal', () => {
            if (this.props.isShown) {
                this.props.hide();
            }
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
        const node = ReactDOM.findDOMNode(this);
        $(node).modal('show');
    }

    hideModal() {
        const node = ReactDOM.findDOMNode(this);
        $(node).modal('hide');
    }

    handleSubmit() {
        let username = ReactDOM.findDOMNode(this.refs.username).value;
        let email = ReactDOM.findDOMNode(this.refs.email).value;
        let password = ReactDOM.findDOMNode(this.refs.password).value;
        let confirmPassword = ReactDOM.findDOMNode(this.refs.confirm_password).value;
        let user = {
            username: username,
            email: email,
            password: password,
            confirmPassword: confirmPassword
        };
        this.props.signUp(user);
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
                            <h4 className="modal-title" id="myModalLabel">Registration</h4>
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
                                    <label htmlFor="email">Email</label>
                                    <input type="email"
                                           className="form-control"
                                           name="email"
                                           placeholder="Email"
                                           ref="email"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="password">Password</label>
                                    <input type="password"
                                           className="form-control"
                                           name="password"
                                           placeholder="Password"
                                           ref="password"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="confirm_password">Confirm Password</label>
                                    <input type="password"
                                           className="form-control"
                                           name="confirm_password"
                                           placeholder="Confirm Password"
                                           ref="confirm_password"/>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit"
                                    className="btn btn-primary"
                                    onClick={() => ::this.handleSubmit()}>
                                Sign Up
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}