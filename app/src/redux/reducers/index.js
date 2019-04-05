import { combineReducers } from 'redux'
import { hasErrored, isLoading,segments } from './sotmReducer';

export default combineReducers({ hasErrored, isLoading, segments });