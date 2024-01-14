import React from 'react';
import { Link } from 'react-router-dom'; // react-router-dom을 사용한 라우팅
import '../css/style.css'; // CSS 파일 경로를 적절하게 조정하세요.
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
    </header>
  );
};

export default Header;
