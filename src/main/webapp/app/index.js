import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Router, Route, IndexRoute, browserHistory } from 'react-router';

import configureStore from './store/configureStore';
import AppContainer from './containers/AppContainer';
import ChatContainer from './containers/ChatContainer';
import CommunityThumbnailBoxContainer from './containers/CommunityThumbnailBoxContainer';
import CommunityContainer from './containers/CommunityContainer';
import NotFound from './components/NotFound';
import ChannelContainer from './containers/ChannelContainer';
import SearchResultListContainer from './containers/SearchResultListContainer';

import 'font-awesome/css/font-awesome.min.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap';

let store = configureStore();

ReactDOM.render(
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/" component={AppContainer}>
        <IndexRoute component={CommunityThumbnailBoxContainer}/>
        <Route path="community/:community" component={CommunityContainer}/>
        <Route path="community/:community/channel" component={ChatContainer}>
          <Route path=":channel" component={ChannelContainer}/>
        </Route>
        <Route path="search" component={SearchResultListContainer}/>
        <Route path="*" component={NotFound}/>
      </Route>
    </Router>
  </Provider>,
  document.getElementById('root')
);
