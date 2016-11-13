import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import NavBar from "../components/NavBar";
import * as userActions from "../actions/UserActions";
import * as viewActions from "../actions/ViewActions";

const mapStateToProps = (state) => {
    return {
        logInDialog: {
            isShown: state.viewReducer.isShownLogInDialog
        },
        signUpDialog: {
            isShown: state.viewReducer.isShownSignUpDialog
        },
        isAuthorized: state.usersReducer.isAuthorized
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
            hide: bindActionCreators(viewActions.hideSignUpDialog, dispatch)
        },
        logOut: bindActionCreators(userActions.logOut, dispatch)
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NavBar)