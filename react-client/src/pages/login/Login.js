import React, { useState } from 'react';
import axios from 'axios';
import { loginService } from '../../service/loginService';
import '../../css/style.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [responseStatus, setResponseStatus] = useState(null);
  const [responseMessage, setResponseMessage] = useState('');


  const handleLogin = async (e) => {
    e.preventDefault();
    
    try {
      const response = await loginService.login(username, password);
        setResponseStatus(response.status);
        setResponseMessage(response.data);
    } catch (error) {
      console.error('로그인 요청 중 오류 발생:', error);
      setErrorMessage('로그인 중 오류가 발생했습니다.');
    }
  };

  const handleCloseErrorModal = () => {
    setErrorMessage('');
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2>로그인</h2>
        <form onSubmit={handleLogin}>
          <label htmlFor="username">아이디</label>
          <input
            type="text"
            id="username"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />

          <label htmlFor="password">비밀번호</label>
          <input
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button type="submit" className="login-button">
            로그인
          </button>
        </form>
      </div>

      {errorMessage && (
        <div className="error-modal">
          <p>{errorMessage}</p>
          <button onClick={handleCloseErrorModal}>닫기</button>
        </div>
      )}
    </div>
  );
};

export default Login;
