import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginService } from '../../service/loginService';
import '../../css/style.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await loginService.login(username, password);

      if (response.status === 200) {
        // 로그인 성공 시 페이지 이동
        navigate(-1);
      }

    } catch (error) {
      console.error('로그인 요청 중 오류 발생:', error);
      setErrorMessage('존재하지 않는 유저 정보입니다.');

      // 새로운 창에 알림 띄우기
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
