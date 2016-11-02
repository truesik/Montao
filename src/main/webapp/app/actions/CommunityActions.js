import * as actionTypes from "../constants/communityConstants";

var csrfToken = csrf;
var csrfHeader = 'X-CSRF-TOKEN';
var headers = {};
headers[csrfHeader] = csrfToken;

export const getCommunities = (startRowPosition) => {
    return dispatch => {
        dispatch({
            type: actionTypes.GET_COMMUNITIES_REQUEST
        });
        $
            .ajax({
                url: '/api/community/get_all',
                type: 'post',
                data: {startRowPosition: startRowPosition},
                headers: headers
            })
            .then(communities => {
                dispatch({
                    type: actionTypes.GET_COMMUNITIES_SUCCESS,
                    payload: communities
                })
            })
            .fail(error => {
                dispatch({
                    type: actionTypes.GET_COMMUNITIES_FAILURE,
                    payload: error
                })
            })
    }
};