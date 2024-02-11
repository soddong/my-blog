import axios from 'axios';

const API_URL = 'http://localhost:8080/';

const createPost = post => axios.post(`${API_URL}posts`, post);

const getPosts = (categoryId = null) => {
  const url = categoryId ? `${API_URL}categories/${categoryId}/posts` : `${API_URL}posts`;
  return axios.get(url);
};

const getPost = postId => axios.get(`${API_URL}posts/${postId}`);

const updatePost = (postId, post) => axios.put(`${API_URL}posts/${postId}`, post);

const deletePost = postId => axios.delete(`${API_URL}posts/${postId}`);

export const postService = {
  createPost,
  getPosts,
  getPost,
  updatePost,
  deletePost
};
