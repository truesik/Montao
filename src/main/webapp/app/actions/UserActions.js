import {GET_USERS_REQUEST, GET_USERS_SUCCESS, GET_USERS_FAILURE} from "../constants/User";

export const getUsers = () => {
    return (dispatch) => {
        dispatch({
            type: GET_USERS_REQUEST
        });
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $
            .ajax({
                url: '/api/user/get_subscribed_users?communityTitle=' + $(location).attr('pathname').substring(1),
                type: 'post',
                headers: headers
            })
            .then(response => {
                    dispatch({
                        type: GET_USERS_SUCCESS,
                        payload: response
                    })
                }
            )
            .fail(error => {
                    dispatch({
                        type: GET_USERS_FAILURE,
                        payload: error
                    })
                }
            );
    };
};