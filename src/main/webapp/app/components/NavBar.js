import React from "react";
import {Link} from "react-router";

import SignUpForm from "./SignUpForm";
import LogInForm from "./LogInForm";
import AddCommunityForm from "./AddCommunityForm";

export default class NavBar extends React.Component {
    render() {
        let navBarTemplate;
        if (this.props.isAuthorized) {
            navBarTemplate = (
                <div className="collapse navbar-collapse" id="navbar">
                    <ul className="nav navbar-nav navbar-right">
                        <li className="dropdown">
                            <a className="dropdown-toggle"
                               data-toggle="dropdown"
                               role="button"
                               aria-haspopup="true"
                               aria-expanded="false">
                                {this.props.username}
                                <span className="caret"></span>
                            </a>
                            <ul className="dropdown-menu">
                                <li><a>Profile</a></li>
                                <li><a onClick={() => this.props.addCommunityDialogActions.show()}>New Community</a></li>
                                <li role="separator" className="divider"></li>
                                <li><a>Setting</a></li>
                                <li><a onClick={() => this.props.logOut()}>Log out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            )
        } else {
            navBarTemplate = (
                <div className="collapse navbar-collapse" id="navbar">
                    <ul className="nav navbar-nav navbar-right">
                        <li><a onClick={() => this.props.signUpDialogActions.show()}>Sign up</a></li>
                        <li><a onClick={() => this.props.logInDialogActions.show()}>Log in</a></li>
                    </ul>
                </div>
            )
        }
        return (
            <div>
                <SignUpForm {...this.props.signUpDialog} {...this.props.signUpDialogActions}/>
                <LogInForm {...this.props.logInDialog} {...this.props.logInDialogActions}/>
                {this.props.isAuthorized && <AddCommunityForm {...this.props.addCommunityDialog}
                                                              {...this.props.addCommunityDialogActions}
                                                              username={this.props.username}/>}
                <nav className="navbar navbar-inverse navbar-fixed-top">
                    <div className="container-fluid">
                        <div className="navbar-header">
                            <button type="button"
                                    className="navbar-toggle collapsed"
                                    data-toggle="collapse"
                                    data-target="#navbar"
                                    aria-expanded="false">
                                <span className="sr-only">Toggle navigation</span>
                                <span className="icon-bar"></span>
                                <span className="icon-bar"></span>
                                <span className="icon-bar"></span>
                            </button>
                            <Link to="/"><span className="navbar-brand">Montao</span></Link>
                        </div>
                        {navBarTemplate}
                    </div>
                </nav>
            </div>
        )
    }
}