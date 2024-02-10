import React, { useState } from 'react';
import '../css/post.css'; // post.css 파일 import

const PostForm = ({ onAddPost, categories }) => {
  const [post, setPost] = useState({ id: null, categoryId: '', title: '', content: '' });

  const handleChange = (e) => {
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onAddPost(post);
    setPost({ id: null, title: '', content: '', categoryId: '' });
  };

  return (
    <div className="post-form"> {/* post-form 클래스 추가 */}
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="title"
          value={post.title}
          onChange={handleChange}
          placeholder="제목"
        />
        <textarea
          name="content"
          value={post.content}
          onChange={handleChange}
          placeholder="내용"
        />
        <select
          name="categoryId"
          value={post.categoryId}
          onChange={handleChange}
        >
          <option value="">카테고리 선택</option>
          {categories.map((category) => (
            <option key={category.id} value={category.id}>
              {category.name}
            </option>
          ))}
        </select>
        <button type="submit">게시글 등록</button>
      </form>
    </div>
  );
};

export default PostForm;
