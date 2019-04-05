import React from 'react';
import { Link } from 'react-router-dom'


export default function Header() {
    return (
        <div>
            <h1>BMCC</h1>
            <Link to="/sotm">Segment of the month</Link> | 
            <Link to="/links">Links</Link> |
            <Link to="/admin">Admin</Link>
        </div>
    );
}
