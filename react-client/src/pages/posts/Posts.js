// Posts.js
import React, { useEffect, useState } from 'react';
import { categoryService } from '../../service/categoryService';
import { postService } from '../../service/postService';
import PostForm from '../../components/PostForm';
import '../../css/post.css';
import { useNavigate } from 'react-router-dom';
import { useLoginContext } from '../login/LoginContext';

const Posts = () => {
  const navigate = useNavigate();
  const { loginSession } = useLoginContext();

  const [categories, setCategories] = useState([]);
  const [posts, setPosts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedPostId, setSelectedPostId] = useState(null);
  const [isExpandedPost, setIsExpandedPost] = useState(false);
  const [isExpanded, setIsExpanded] = useState(false);

  useEffect(() => {
    loadCategories();
    loadInitialPosts();
  }, []);

  const loadInitialPosts = async () => {
    try {
      const response = await postService.getPosts();
      setPosts(response.data);
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
  };

  const loadCategories = async () => {
    try {
      const response = await categoryService.getCategories();
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const onSelectCategory = async (categoryId) => {
    try {
      setSelectedCategory(categoryId);
      const response = await postService.getPosts(categoryId);
      setPosts(response.data);
      closeExpandedView();
    } catch (error) {
      console.error(`Error fetching posts for category ${categoryId}:`, error);
    }
  };

  const onSelectPost = (postId) => {
    setSelectedPostId(postId);
    setIsExpandedPost(true);
  };

  const closeExpandedView = () => {
    setSelectedPostId(null);
    setIsExpandedPost(false);
    navigate('/posts');
  };

  const addPost = async (newPost) => {
    try {
      const response = await postService.addPost(newPost);
      loadInitialPosts();
    } catch (error) {
      console.error('Error adding post:', error);
    }
  };

  const selectedPost = posts.find((post) => post.id === selectedPostId);

  return (
    <div>
      <aside>
        <div className="category-box">
          <h2>카테고리</h2>
          <ul id="category-list">
            <li key="all" onClick={() => onSelectCategory(null)}>
              전체보기
            </li>
            {categories
              .filter((category) => category.name !== 'default')
              .map((category) => (
                <li key={category.id} onClick={() => onSelectCategory(category.id)}>
                  {category.name}
                </li>
              ))}
          </ul>
        </div>
      </aside>

      <main>
        <div className="posts-container">
          {loginSession && (
            <div className='add-post-button'>
              <button onClick={() => setIsExpanded(true)}>게시글 등록하기</button>
            </div>
          )}

          {posts.map((post) => (
            <div
              key={post.id}
              className={`post-item ${selectedPostId === post.id && isExpandedPost ? 'expanded' : ''}`}
              onClick={() => onSelectPost(post.id)}
            >
              <h3>{post.title}</h3>
              {selectedPostId === post.id && isExpandedPost && (
                <div className="expanded-content">
                  <button className="close-btn" onClick={closeExpandedView}>
                    Close
                  </button>
                  {selectedPost ? (
                    <>
                      <p>{selectedPost.content}</p>
                    </>
                  ) : (
                    <p>{post.content}</p>
                  )}
                </div>
              )}
            </div>
          ))}
          {isExpanded && loginSession && <PostForm onAddPost={addPost} categories={categories} className='post-form' />}

        </div>
      </main>
    </div>
  );
};

export default Posts;
