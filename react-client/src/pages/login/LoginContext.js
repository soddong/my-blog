import React, { createContext, useContext, useState, useEffect } from 'react';
import { loginService } from '../../service/loginService';

const LoginContext = createContext();

export const useLoginContext = () => {
  return useContext(LoginContext);
};

export const LoginProvider = ({ children }) => {
  const [loginSession, setLoginSession] = useState(false);

  useEffect(() => {
    const checkSession = async () => {
      try {
        const response = await loginService.fetchSession();
        if (response.status === 200) {
          setLoginSession(true);
        } else {
          setLoginSession(false);
        }
      } catch (error) {
        console.error('세션 확인 실패:', error);
        setLoginSession(false);
      }
    };

    checkSession();
  }, []);

  const login = () => {
    setLoginSession(true);
  };

  const logout = () => {
    setLoginSession(false);
  };

  return (
    <LoginContext.Provider value={{ loginSession, login, logout }}>
      {children}
    </LoginContext.Provider>
  );
};

export default LoginContext;