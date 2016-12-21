import {connect} from 'react-redux';
import {bindActionCreators} from "redux";

import Community from '../components/Community';
import * as communityActions from '../actions/CommunityActions';

const mapStateToProps = (state) => {
    return {
        community: state.communitiesReducer.get('currentCommunity'),
        valid: state.communitiesReducer.get('valid')
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        checkCommunityExistence: bindActionCreators(communityActions.checkCommunity, dispatch)
    }
};


export default connect(mapStateToProps, mapDispatchToProps)(Community);