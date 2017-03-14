import { fromJS, Map } from 'immutable';

import * as actionTypes from '../constants/communityConstants';

const initialState = fromJS({
  communities: [],
  error: '',
  communityPath: '',
  isSubscribed: false,
  currentCommunity: Map(),
  valid: false,
  notFound: false
});

const communitiesReducer = (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.GET_COMMUNITIES_REQUEST:
      return state;
    case actionTypes.GET_COMMUNITIES_SUCCESS:
      return state.set('communities', fromJS(action.payload));
    case actionTypes.GET_COMMUNITIES_FAILURE:
      return state.set('error', action.payload);
    case actionTypes.JOIN_REQUEST:
      return inProgress(state, action);
    case actionTypes.JOIN_SUCCESS:
      return changeSubscriptionStatus(state, action);
    case actionTypes.JOIN_FAILURE:
      return state.set('error', action.payload);
    case actionTypes.LEAVE_REQUEST:
      return inProgress(state, action);
    case actionTypes.LEAVE_SUCCESS:
      return changeSubscriptionStatus(state, action);
    case actionTypes.LEAVE_FAILURE:
      return state.set('error', action.payload);
    case actionTypes.ADD_COMMUNITY_REQUEST:
      return state;
    case actionTypes.ADD_COMMUNITY_SUCCESS:
      return state.set('communityPath', action.payload);
    case actionTypes.ADD_COMMUNITY_FAILURE:
      return state.set('error', action.payload);
    case actionTypes.CHECK_SUBSCRIPTION_REQUEST:
      return state;
    case actionTypes.CHECK_SUBSCRIPTION_SUCCESS:
      return state.set('isSubscribed', action.payload == 'true');
    case actionTypes.CHECK_SUBSCRIPTION_FAILURE:
      return state.set('isSubscribed', false);
    case actionTypes.CHECK_COMMUNITY_REQUEST:
      return state.set('valid', false).set('notFound', false);
    case actionTypes.CHECK_COMMUNITY_SUCCESS:
      return state.set('currentCommunity', Map(action.payload)).set('valid', true).set('notFound', false);
    case actionTypes.CHECK_COMMUNITY_FAILURE:
      return state.set('error', action.payload).set('notFound', true);
    default:
      return state;
  }
};

const inProgress = (state, action) => {
  const index = state.get('communities').findIndex(community => community.get('title') === action.payload);
  return state.setIn(['communities', index, 'inProgress'], true);
};

const changeSubscriptionStatus = (state, action) => {
  const index = state.get('communities').findIndex(community => community.get('id') === action.payload.id);
  return state
    .setIn(['communities', index, 'subscribed'], action.payload.subscribed)
    .setIn(['communities', index, 'inProgress'], false);
};

export default communitiesReducer;
