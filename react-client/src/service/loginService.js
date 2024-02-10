import axios from 'axios';

const API_URL = 'http://localhost:8080/';

const login = async (name, password) => {
  try {
    const response = await axios.post(`${API_URL}api/auth/login`, { name, password });
    console.log(response.status); 
    return response.data; 
  } catch (error) {
    console.error('로그인 요청 중 오류 발생:', error);
    throw error; // 오류를 상위로 전파하거나, 다르게 처리
  }
};
export const loginService = {
  login
};
