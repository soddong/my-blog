import React, { createContext, useContext, useState } from 'react';

const LoginContext = createContext();

export const useLoginContext = () => {
  return useContext(LoginContext);
};

export const LoginProvider = ({ children }) => {
  const [loginSession, setLoginSession] = useState(false);

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