//import UserProfileActions from "../actions/UserProfileActions";
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as userPageActions from '../actions/UserPageActions'
import UserProfilePage from '../components/UserProfilePage';

const mapStateToProps = (state) => {
    return {
        profile: state.profile
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        userPageActions: bindActionCreators(userPageActions, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserProfilePage);
