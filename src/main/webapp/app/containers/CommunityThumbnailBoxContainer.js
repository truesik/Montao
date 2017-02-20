import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as communityActions from '../actions/CommunityActions';

import CommunityThumbnailBox from '../components/CommunityThumbnailBox';

const mapStateToProps = (state) => {
  return {
    communities: state.communitiesReducer.get('communities').toArray(),
    isAuthorized: state.usersReducer.get('isAuthorized')
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCommunities: bindActionCreators(communityActions.getCommunities, dispatch),
    join: bindActionCreators(communityActions.join, dispatch),
    leave: bindActionCreators(communityActions.leave, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CommunityThumbnailBox);
