import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import NavBar from "../components/NavBar";
import * as userActions from "../actions/UserActions";
import * as viewActions from "../actions/ViewActions";
import * as communityActions from "../actions/CommunityActions";

const mapStateToProps = (state) => {
    return {
        logInDialog: {
            isShown: state.viewReducer.isShownLogInDialog
        },
        signUpDialog: {
            isShown: state.viewReducer.isShownSignUpDialog
        },
        addCommunityDialog: {
            isShown: state.viewReducer.isShownAddCommunityDialog
        },
        isAuthorized: state.usersReducer.get('isAuthorized'),
        username: state.usersReducer.get('username')
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        logInDialogActions: {
            show: bindActionCreators(viewActions.showLogInDialog, dispatch),
            hide: bindActionCreators(viewActions.hideLogInDialog, dispatch),
            logIn: bindActionCreators(userActions.logIn, dispatch)
        },
        signUpDialogActions: {
            show: bindActionCreators(viewActions.showSignUpDialog, dispatch),
            hide: bindActionCreators(viewActions.hideSignUpDialog, dispatch),
            signUp: bindActionCreators(userActions.addUser, dispatch)
        },
        addCommunityDialogActions: {
            show: bindActionCreators(viewActions.showAddCommunityDialog, dispatch),
            hide: bindActionCreators(viewActions.hideAddCommunityDialog, dispatch),
            addCommunity: bindActionCreators(communityActions.add, dispatch)
        },
        logOut: bindActionCreators(userActions.logOut, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NavBar)