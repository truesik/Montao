//import UserProfileActions from "../actions/UserProfileActions";
import UserProfilePage from '../components/UserProfilePage';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as UserPageActions from '../actions/UserPageActions';

const mapStateToProps = (state) => {
  return {
    profile: state.profile
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    UserPageActions: bindActionCreators(UserPageActions, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserProfilePage);
