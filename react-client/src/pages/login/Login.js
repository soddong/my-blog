import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLoginContext } from './LoginContext';
import { loginService } from '../../service/loginService';
import '../../css/style.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();
  const { login } = useLoginContext();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await loginService.login(username, password);
      if (response.status === 200) {
        login();
        navigate('/posts');
      }
    } catch (error) {
      console.error('로그인 요청 중 오류 발생:', error);
      setErrorMessage('존재하지 않는 유저 정보입니다.');
      window.alert(errorMessage);
    }
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
    </div>
  );
};

export default Login;
