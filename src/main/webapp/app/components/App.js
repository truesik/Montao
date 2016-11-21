import React from 'react';
import NavBarContainer from "../containers/NavBarContainer";

export default class App extends React.Component {
    componentDidMount() {
        this.props.checkAuthorization();
    }

    render() {
        return (
            <div>
                <NavBarContainer />
                {this.props.children}
            </div>
        )
    }
}