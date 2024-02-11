import React, { useState, useEffect } from 'react';
import '../css/post.css';
import { postService } from '../service/postService';
import PostForm from './PostForm';

const UpdateForm = ({ postId }) => {
    const [updatedPost, setUpdatedPost] = useState({ id: postId, categoryId: '', title: '', content: '' });

    const handleChange = (e) => {
      setUpdatedPost({ ...updatedPost, [e.target.name]: e.target.value });
    };
  
    const handleUpdateSubmit = (e) => {
      e.preventDefault();
      postService.updatePost(updatedPost.id, updatedPost);
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