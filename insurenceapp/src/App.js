import React from 'react';
import { Routes, Route, Link, useLocation } from 'react-router-dom';
import Insurencereg from './Components/Insurencereg';
import SearchFunction from './Components/SerachFunction';
import './App.css';
import Button from 'react-bootstrap/Button';

function App() {
  const currentLocation = useLocation();
  return (
    <div className="nsk" >
      <Routes>
        <Route path="/" element={<Insurencereg />} />
        <Route path="/search" element={<SearchFunction />} />
      </Routes>
      {currentLocation.pathname !== '/search' && (
        <Link to="/search" style={{ display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: '50px'}}>
        <Button variant="primary">Click for Search</Button>
        </Link>
      )}
    </div>
  );
}
export default App;
