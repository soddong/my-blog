import React from 'react';
import '../../css/style.css'; 

const Login = () => {
    return (
      <div className="login-container">
  
        <div className="login-form">
          <h2>로그인</h2>
          <form>
            <label htmlFor="username">아이디</label>
            <input type="text" id="username" name="username" />
  
            <label htmlFor="password">비밀번호</label>
            <input type="password" id="password" name="password" />
  
            <button type="submit" className="login-button">로그인</button>
          </form>
        </div>
      </div>
    );
  };

export default Login;