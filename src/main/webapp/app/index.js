import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {Router, Route, IndexRoute, browserHistory} from "react-router";
import App from './components/App';
import ChatContainer from "./containers/ChatContainer";
import CommunityBoxContainer from "./containers/CommunityBoxContainer";
import configureStore from "./store/configureStore";

let store = configureStore();

ReactDOM.render(
    <Provider store={store}>
        <Router history={browserHistory}>
            <Route path="/" component={App}>
                <IndexRoute component={CommunityBoxContainer} />
                <Route path="community/:community" component={ChatContainer} />
            </Route>
        </Router>
    </Provider>,
    document.getElementById('root')
);
