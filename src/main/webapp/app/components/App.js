import React from 'react'
import NavBar from './NavBar'
import ChatContainer from '../containers/ChatContainer'

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
                <ChatContainer currentCommunityTitle={$(location).attr('pathname').substring(1)}/>
            </div>
        )
    }
}