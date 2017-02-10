import * as actionTypes from "../constants/searchConstants";

export const search = (query) => {
    const SEARCH_REQUEST = new Request('.../api/search?q=myCommunityTitle', {
        method: 'POST',
        body: `myCommuniyTitle=${communityTitle}`,
        headers: headers,
        credentials: 'same-origin'
    });
    return fetch(SEARCH_REQUEST)
        .then(response => {
            dispatch({
                type:constants.SEARCH_SUCCESS
            })
        })
        .catch(error =>{
            dispatch({
                type:constants.SEARCH_FAILURE
            })
        })
}