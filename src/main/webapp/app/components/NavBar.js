import React from "react";

export default class NavBar extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <nav className="navbar navbar-inverse navbar-fixed-top">
                <div className="container-fluid">
                    <div className="navbar-header">
                        <button type="button"
                                className="navbar-toggle collapsed"
                                data-toggle="collapse"
                                data-target="#notsigned-navbar"
                                aria-expanded="false">
                            <span className="sr-only">Toggle navigation</span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                        </button>
                        <a className="navbar-brand" href="/">Montao</a>
                    </div>

                    <div className="collapse navbar-collapse" id="notsigned-navbar">
                        <ul className="nav navbar-nav navbar-right">
                            <li><a href="/registration" >Sign up</a></li>
                            <li><a href="/login" >Log in</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        )
    }
}
