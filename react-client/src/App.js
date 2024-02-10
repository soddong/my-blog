import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { LoginProvider } from './pages/login/LoginContext'; 
import Header from './components/Header'; 
import Home from './pages/home/Home'; 
import Login from './pages/login/Login'; 
import Posts from './pages/posts/Posts'; 

const App = () => {
  return (
    <Router>
      <LoginProvider>
        <Header />
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/posts" element={<Posts />} />
        </Routes>
      </LoginProvider>
    </Router>
  );
};

export default App;
