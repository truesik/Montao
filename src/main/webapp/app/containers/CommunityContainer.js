import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as communityActions from '../actions/CommunityActions';
import * as viewActions from '../actions/ViewActions';
import * as userActions from '../actions/UserActions';

import Community from '../components/Community';

const mapStateToProps = (state) => {
  return {
    community: state.communitiesReducer.get('currentCommunity'),
    valid: state.communitiesReducer.get('valid'),
    notFound: state.communitiesReducer.get('notFound'),
    channelListDialog: {
      isShown: state.viewReducer.get('isShownChannelListDialog')
    },
    userListDialog: {
      isShown: state.viewReducer.get('isShownUserListDialog')
    },
    subscribers: state.usersReducer.get('subscribers').toArray()
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    checkCommunityExistence: bindActionCreators(communityActions.checkCommunity, dispatch),
    channelListDialogActions: {
      show: bindActionCreators(viewActions.showChannelListDialog, dispatch),
      hide: bindActionCreators(viewActions.hideChannelListDialog, dispatch)
    },
    userListDialogActions: {
      show: bindActionCreators(viewActions.showUserListDialog, dispatch),
      hide: bindActionCreators(viewActions.hideUserListDialog, dispatch)
    },
    getUsers: bindActionCreators(userActions.getUsers, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Community);
