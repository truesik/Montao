import * as actionTypes from '../constants/viewConstants';

export const showLogInDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SHOW_LOG_IN_DIALOG
    });
  };
};

export const hideLogInDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.HIDE_LOG_IN_DIALOG
    });
  };
};

export const showSignUpDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SHOW_SIGN_UP_DIALOG
    });
  };
};

export const hideSignUpDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.HIDE_SIGH_UP_DIALOG
    });
  };
};

export const showAddCommunityDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SHOW_ADD_COMMUNITY_DIALOG
    });
  };
};

export const hideAddCommunityDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.HIDE_ADD_COMMUNITY_DIALOG
    });
  };
};

export const showAddChannelDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SHOW_ADD_CHANNEL_DIALOG
    });
  };
};

export const hideAddChannelDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.HIDE_ADD_CHANNEL_DIALOG
    });
  };
};

export const showChannelListDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SHOW_CHANNEL_LIST_DIALOG
    });
  };
};

export const hideChannelListDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.HIDE_CHANNEL_LIST_DIALOG
    });
  };
};

export const showUserListDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.SHOW_USER_LIST_DIALOG
    });
  };
};

export const hideUserListDialog = () => {
  return (dispatch) => {
    dispatch({
      type: actionTypes.HIDE_USER_LIST_DIALOG
    });
  };
};
