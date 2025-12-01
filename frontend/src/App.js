import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login';
import SkateList from './SkateList';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/skates" element={<SkateList />} />
      </Routes>
    </Router>
  );
}

export default App;