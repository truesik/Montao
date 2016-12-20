import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {Router, Route, IndexRoute, browserHistory} from "react-router";
import AppContainer from './containers/AppContainer';
import ChatContainer from "./containers/ChatContainer";
import CommunityThumbnailBoxContainer from "./containers/CommunityThumbnailBoxContainer";
import configureStore from "./store/configureStore";

let store = configureStore();

ReactDOM.render(
    <Provider store={store}>
        <Router history={browserHistory}>
            <Route path="/" component={AppContainer}>
                <IndexRoute component={CommunityThumbnailBoxContainer} />
                <Route path="community/:community" component={ChatContainer} />
            </Route>
        </Router>
    </Provider>,
    document.getElementById('root')
);
