import React from 'react';
import { Link } from 'react-router-dom';
import { useLoginContext } from '../pages/login/LoginContext'; // 적절한 경로로 수정
import '../css/style.css';
import profileImage from '../assets/profile.png';
import { loginService } from '../service/loginService';

const Header = () => {
  const { loginSession, logout } = useLoginContext();

  const handleLogout = async () => {
    try {
      await loginService.logout();
      logout();
    } catch (error) {
      console.error('로그아웃 중 오류 발생:', error);
    }
  };

  return (
    <header>
      <img src={profileImage} alt="프로필 사진" />
      <nav>
        <ul>
          <li><Link to="/home" id="home">Home</Link></li>
          <li><Link to="/posts" id="post">Post</Link></li>
        </ul>
      </nav>
      <div className="login-button-container">
        {loginSession ? (
          // 로그인 상태에 따라 버튼 변경
          <button onClick={handleLogout} className="login-button">로그아웃</button>
        ) : (
          <Link to="/login" className="login-button">로그인</Link>
        )}
      </div>
    </header>
  );
};

export default Header;
