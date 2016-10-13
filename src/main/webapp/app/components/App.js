import React from 'react'
import NavBar from './NavBar'
import Chat from './Chat'

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isAuthorized: false,
            username: ''
        }
    }
    render() {
        return (
            <div>
                <NavBar isAuthorized={this.state.isAuthorized} username={this.state.username}/>
                <Chat/>
            </div>
        )
    }
}