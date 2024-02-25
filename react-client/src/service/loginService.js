import axios from 'axios';

const API_URL = 'http://localhost:8080/';

const login = async (name, password) => {
  try {
    const response = await axios.post(`${API_URL}api/auth/login`, { name, password });
    console.log(response.status); 
    return response; 
  } catch (error) {
    console.error('로그인 요청 중 오류 발생:', error);
    throw error; // 오류를 상위로 전파하거나, 다르게 처리
  }
};

const fetchSession = async () => {
  try {
    const response = await axios.get(`${API_URL}api/auth/session`,{ withCredentials: true });
    console.log(response.data); // 성공적으로 응답을 받은 경우
    return response;
  } catch (error) {
    console.error(error); // 요청 중 에러가 발생한 경우
    throw error;
  }
};

export const loginService = {
  login,
  fetchSession
};
