import React from 'react';
import { Link } from 'react-router-dom';
import '../css/style.css';
import profileImage from '../assets/profile.png';

const Header = () => {
  return (
    <header>
      <img src={profileImage} alt="프로필 사진" />
      <nav>
        <ul>
          <li><Link to="../pages/home" id="home">Home</Link></li>
          <li><Link to="../pages/posts" id="post">Post</Link></li>
        </ul>
      </nav>
      <div className="login-buttone-container">
        {/* 로그인 창 또는 버튼을 추가하세요. */}
        <Link to="../pages/login" className="login-button">로그인</Link>
      </div>
    </header>
  );
};

export default Header;
