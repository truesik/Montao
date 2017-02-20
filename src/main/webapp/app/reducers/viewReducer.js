import { fromJS } from 'immutable';

import * as actionTypes from '../constants/viewConstants';

const initialState = fromJS({
  isShownLogInDialog: false,
  isShownSignUpDialog: false,
  isShownAddCommunityDialog: false,
  isShownAddChannelDialog: false,
  isShownChannelListDialog: false,
  isShownUserListDialog: false
});

const viewReducer = (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.SHOW_LOG_IN_DIALOG:
      return state.set('isShownLogInDialog', true);
    case actionTypes.HIDE_LOG_IN_DIALOG:
      return state.set('isShownLogInDialog', false);
    case actionTypes.SHOW_SIGN_UP_DIALOG:
      return state.set('isShownSignUpDialog', true);
    case actionTypes.HIDE_SIGH_UP_DIALOG:
      return state.set('isShownSignUpDialog', false);
    case actionTypes.SHOW_ADD_COMMUNITY_DIALOG:
      return state.set('isShownAddCommunityDialog', true);
    case actionTypes.HIDE_ADD_COMMUNITY_DIALOG:
      return state.set('isShownAddCommunityDialog', false);
    case actionTypes.SHOW_ADD_CHANNEL_DIALOG:
      return state.set('isShownAddChannelDialog', true);
    case actionTypes.HIDE_ADD_CHANNEL_DIALOG:
      return state.set('isShownAddChannelDialog', false);
    case actionTypes.SHOW_CHANNEL_LIST_DIALOG:
      return state.set('isShownChannelListDialog', true);
    case actionTypes.HIDE_CHANNEL_LIST_DIALOG:
      return state.set('isShownChannelListDialog', false);
    case actionTypes.SHOW_USER_LIST_DIALOG:
      return state.set('isShownUserListDialog', true);
    case actionTypes.HIDE_USER_LIST_DIALOG:
      return state.set('isShownUserListDialog', false);
    default:
      return state;
  }
};

export default viewReducer;
