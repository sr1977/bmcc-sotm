import React from 'react';
import Header from './Header';
import { Provider } from 'react-redux'
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Sotm from './sotm/SegmentOfTheMonth';
import store from '../redux/store'


function App(props) {
    return (
        <Router>
            <Provider store={store}>
                <Header />
                <Route path="/sotm" component={Sotm} />
            </Provider>
        </Router>
    );
}

App.propTypes = {};

export default App;
