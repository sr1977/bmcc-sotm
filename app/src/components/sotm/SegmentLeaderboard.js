import React from 'react';
import PropTypes from 'prop-types';

function NoLeaderboard(props) {
    return (
        <h4>Unable to show current leaderboard</h4>
    )
}

function Leaderboard(props) {
    return (
        <div>
            <h3>
                {props.segment.label}: {props.segment.name}
            </h3>
            <ol>
                {props.segment.leaderboard.map(effort => (
                    <li key={effort.id}>
                        {effort.rideDate}: {effort.name} - {effort.segmentTime}
                    </li>
                ))}
            </ol>
        </div>
    );
}

function SegmentLeaderboard(props) {
    return props.segment ? <Leaderboard segment={props.segment} /> : <NoLeaderboard />;
}

SegmentLeaderboard.propTypes = {
    segment: PropTypes.object
};

export default SegmentLeaderboard;
