import {fromJS} from 'immutable';

import * as constants from '../constants/searchConstants';

const initialState = fromJS({
    result: [],
    error: ''
});

const searchReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.SEARCH_REQUEST:
            return state;
        case constants.SEARCH_SUCCESS:
            return state.set('result', fromJS(action.payload));
        case constants.SEARCH_FAILURE:
            return state.set('error', action.payload);
    }
};

export default searchReducer;