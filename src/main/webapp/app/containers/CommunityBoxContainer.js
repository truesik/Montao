import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import CommunitiesBox from "../components/CommunityBox";
import * as communityActions from "../actions/CommunityActions";

const mapStateToProps = (state) => {
    return {
        communities: state.communitiesReducer.communities
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        getCommunities: bindActionCreators(communityActions.getCommunities, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps) (CommunitiesBox)