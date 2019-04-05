import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types'
import { Route } from 'react-router-dom';

import SegmentList from './SegmentList';
import SegmentLeaderboard from './SegmentLeaderboard';
import { fetchData } from '../../redux/actions/sotmActions'

class SegmentOfTheMonth extends Component {
    componentDidMount = () => {
        this.props.fetchData('http://localhost:5000/sotm');
    }
    
    render() {
        if (this.props.hasErrored) {
            return <p>Errored</p>
        }

        if (this.props.isLoading) {
            return <p>Loading...</p>
        }
        else {
            const { segments } = this.props;
            return (
                <div>
                    <h3>Segment of the month</h3>
                    <SegmentList segments={segments} />
                    {segments && (
                        <>
                            <Route
                                path="/sotm/segment/:segmentId"
                                render={({ match: { params } }) => (
                                    <SegmentLeaderboard
                                        segment={segments.find(
                                            s => s.id === parseInt(params.segmentId)
                                        )}
                                    />
                                )}
                            />
                            <Route
                                exact={true}
                                path="/sotm"
                                render={() => (
                                    <SegmentLeaderboard segment={latestSegment(segments)} />
                                )}
                            />
                        </>
                    )}
                </div>
            )
        }
    }
}

const sortSegmentsDateDescending = segments =>
    segments ? segments.sort((a, b) => new Date(b.startDate) - new Date(a.startDate)) : segments;

const latestSegment = segments => (segments && segments[0]) || null;

const mapStateToProps = ( {segments, hasErrored, isLoading }) => {
    return {
        segments: sortSegmentsDateDescending(segments),
        hasErrored,
        isLoading
    };
};

const mapDispatchToProps = dispatch => {
    return {
        fetchData: url => dispatch(fetchData(url))
    }
};

SegmentOfTheMonth.propTypes = {
    segments: PropTypes.array.isRequired,
    hasErrored: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired,
};

export default connect (mapStateToProps, mapDispatchToProps)  (SegmentOfTheMonth)

