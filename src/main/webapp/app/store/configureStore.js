import createLogger from 'redux-logger';
import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';

import reducers from '../reducers';
import { websocketMiddleware } from './websocketMiddleware';

export default function configureStore(initialState) {
  const logger = createLogger();
  return createStore(
    reducers,
    initialState,
    applyMiddleware(thunk, websocketMiddleware, logger)
  );
}
