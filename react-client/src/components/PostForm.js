import React, { useState } from 'react';

const PostForm = ({ onAddPost, categories }) => {
    const [post, setPost] = useState({ id: null, categoryId: '', title: '', content: '' });

    const handleChange = e => {
        setPost({ ...post, [e.target.name]: e.target.value });
    };

    const handleSubmit = e => {
        e.preventDefault();
        onAddPost(post);
        setPost({ id: null, title: '', content: '', categoryId: '' });
    };

    return (
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
                {categories.map(category => (
                    <option key={category.id} value={category.id}>{category.name}</option>
                ))}
            </select>
            <button type="submit">게시글 등록</button>
        </form>
    );
};

export default PostForm;
