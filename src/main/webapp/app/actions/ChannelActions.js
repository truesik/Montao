import {GET_CHANNELS_REQUEST, GET_CHANNELS_SUCCESS, GET_CHANNELS_FAILURE} from "../constants/Channel";

export const getChannels = () => {
    return (dispatch) => {
        dispatch({
            type: GET_CHANNELS_REQUEST
        });
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $
            .ajax({
                url: '/api/channel/get_channels?communityTitle=' + $(location).attr('pathname').substring(1),
                type: 'post',
                headers: headers
            })
            .then(response => {
                dispatch({
                    type:GET_CHANNELS_SUCCESS,
                    payload: response
                })
            })
            .fail(error => {
                dispatch({
                    type: GET_CHANNELS_FAILURE,
                    payload: error
                })
            })
    }
};