import React, { useEffect, useState } from 'react';
import { categoryService } from '../../service/categoryService';
import { postService } from '../../service/postService';
import PostList from '../../components/PostList'; // Import PostList component
import '../../css/post.css';

const Posts = () => {
  const [categories, setCategories] = useState([]);
  const [posts, setPosts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedPostId, setSelectedPostId] = useState(null);
  const [isExpanded, setIsExpanded] = useState(false);
  
  useEffect(() => {
    loadCategories();
    loadInitialPosts();
  }, []);

  // Load initial posts without category filter
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

  const onSelectCategory = async categoryId => {
    try {
      setSelectedCategory(categoryId);
      const response = await postService.getPosts(categoryId);
      setPosts(response.data);
    } catch (error) {
      console.error(`Error fetching posts for category ${categoryId}:`, error);
    }
  };

  const onSelectPost = postId => {
    setSelectedPostId(postId);
    setIsExpanded(true); // Set the post to be expanded
  };

  const closeExpandedView = () => {
    setSelectedPostId(null); // Reset the selected post
    setIsExpanded(false); // Reset the expanded view
  };

  const selectedPost = posts.find(post => post.id === selectedPostId);

  return (
    <div>
      <aside>
        <div className="category-box">
          <h2>카테고리</h2>
          <ul id="category-list">
            {categories.map(category => (
              <li key={category.id} onClick={() => onSelectCategory(category.id)}>
                {category.name}
              </li>
            ))}
          </ul>
        </div>
      </aside>

      <main>
        <div className="posts-container">
          {posts.map(post => (
            <div className={`post-item ${selectedPostId === post.id && isExpanded ? 'expanded' : ''}`} onClick={() => onSelectPost(post.id)}>
            <h3>{post.title}</h3>
            {selectedPostId === post.id && isExpanded && (
                <div className="expanded-content">
                    <button className="close-btn" onClick={closeExpandedView}>Close</button>
                    <p>{selectedPost.content}</p>
                    {/* Add more expanded post details here */}
                </div>
            )}
            </div>
          ))}
        </div>
      </main>
    </div>
  );
};

export default Posts;
