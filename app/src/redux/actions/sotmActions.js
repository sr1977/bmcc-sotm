export const SOTM_STATE_LOADED = 'SOTM_STATE_LOADED'
export const SOTM_IS_LOADING = 'SOTM_IS_LOADING'
export const SOTM_HAS_ERRORED = 'SOTM_HAS_ERRORED'

export function isLoading(loading) {
    return {
        type: SOTM_IS_LOADING,
        payload: loading
    }
}

export function hasErrored(error) {
    return {
        type: SOTM_HAS_ERRORED,
        payload: error
    }
}

export function stateLoaded(segments) {
    return {
        type: SOTM_STATE_LOADED,
        payload: segments
    }
} 

export function fetchData(url) {
    return dispatch => {
        dispatch(isLoading(true));

        fetch(url)
            .then((response) => {
                if (!response.ok) {
                    throw Error(response.statusText);
                }
                dispatch(isLoading(false));
                return response;
            })
            .then(response => response.json())
            .then(segments => dispatch(stateLoaded(segments)))
            .catch(() => dispatch(hasErrored(true)));
    }
}