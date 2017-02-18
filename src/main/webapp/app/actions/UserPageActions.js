import { createAction } from 'redux-actions';

export const MODAL_WINDOW_SHOW = 'MODAL_WINDOW_SHOW';
export const modalWindowShow = createAction(MODAL_WINDOW_SHOW);

export const showUserWindow = () => dispatch => {
            dispatch({
            type: actionTypes.MODAL_WINDOW_SHOW
        });
}