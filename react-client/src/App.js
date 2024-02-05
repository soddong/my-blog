import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/home/Home';
import Posts from './pages/posts/Posts';
import Login from './pages/login/Login';
import Header from './components/Header';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/pages/home" element={<Home />} />
        <Route path="/pages/posts" element={<Posts />} />
        <Route path="/pages/login" element={<Login />} />
      </Routes>
    </Router>
  );
}

export default App;