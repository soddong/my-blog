import React, { useState, useEffect } from 'react';
import '../css/post.css';
import { postService } from '../service/postService';

const UpdateForm = ({ postId, onUpdateSubmit }) => {
  const [updatedPost, setUpdatedPost] = useState({ id: postId, categoryId: '', title: '', content: '' });

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await postService.getPost(postId);
        const { categoryId, title, content } = response.data;
        setUpdatedPost({ id: postId, categoryId, title, content });
      } catch (error) {
        console.error(`Error fetching post ${postId} for update:`, error);
      }
    };

    fetchPost();
  }, [postId]);

  const handleChange = (e) => {
    setUpdatedPost({ ...updatedPost, [e.target.name]: e.target.value });
  };

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    postService.updatePost(updatedPost.id, updatedPost);
    await onUpdateSubmit();
  };

  return (
    <div className="post-form">
      <form onSubmit={handleUpdateSubmit}>
        <input
          type="text"
          name="title"
          value={updatedPost.title}
          onChange={handleChange}
          placeholder="제목"
        />
        <textarea
          name="content"
          value={updatedPost.content}
          onChange={handleChange}
          placeholder="내용"
        />
        <button type="submit">게시글 수정</button>
      </form>
    </div>
  );
};

export default UpdateForm;
