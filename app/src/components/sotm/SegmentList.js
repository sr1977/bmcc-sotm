import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

function SegmentList({ segments }) {
    return (
        <ul>
            {segments.map(segment => (
                <li key={segment.id}>
                    <Link to={`/sotm/segment/${segment.id}`}>
                        {segment.label}: {segment.name}
                    </Link>
                </li>
            ))}
        </ul>
    );
}

SegmentList.propTypes = {
    segments: PropTypes.array.isRequired
};

export default SegmentList;
