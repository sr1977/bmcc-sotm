import {
    SOTM_HAS_ERRORED,
    SOTM_IS_LOADING,
    SOTM_STATE_LOADED
} from '../actions/sotmActions';

export function hasErrored (state = false, action) {
    return action.type === SOTM_HAS_ERRORED ? action.payload : state;
}

export function isLoading (state = false, action) {
    return action.type === SOTM_IS_LOADING ? action.payload : state;
}

export function segments (state = [], action) {
    switch (action.type) {
        case SOTM_STATE_LOADED:
            return action.payload;
        default:
            return state;
    }
}
