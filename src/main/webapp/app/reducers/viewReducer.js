import * as actionTypes from "../constants/viewConstants";

const initialState = {
    isShownLogInDialog: false,
    isShownSignUpDialog: false
};

const viewReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.SHOW_LOG_IN_DIALOG:
            return {
                ...state,
                isShownLogInDialog: true
            };
        case actionTypes.HIDE_LOG_IN_DIALOG:
            return {
                ...state,
                isShownLogInDialog: false
            };
        case actionTypes.SHOW_SIGN_UP_DIALOG:
            return {
                ...state,
                isShownSignUpDialog: true
            };
        case actionTypes.HIDE_SIGH_UP_DIALOG:
            return {
                ...state,
                isShownSignUpDialog: false
            };
        default:
            return state;
    }
};

export default viewReducer;
