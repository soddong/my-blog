import React, { useEffect, useState } from 'react';
import { categoryService } from '../../service/categoryService';
import { postService } from '../../service/postService';
import PostForm from '../../components/PostForm';
import UpdateForm from '../../components/UpdateForm';
import CategoryForm from '../../components/CategoryForm';
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
  const [updatePostId, setUpdatePostId] = useState(null);
  const [isExpandedPost, setIsExpandedPost] = useState(false);
  const [isExpandedCategoryForm, setIsExpandedCategoryForm] = useState(false);
  const [isExpandedEditing, setIsExpandedEditing] = useState(false);
  const [isExpandedCreate, setIsExpandedCreate] = useState(false);
  const [postToEdit, setPostToEdit] = useState(null);

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

  const onSelectPost = async (postId) => {
    setSelectedPostId(postId);
    setIsExpandedPost(true);
  };

  const onDeletePost = async (postId) => {
    try {
      await postService.deletePost(postId);
      loadInitialPosts();
      closeExpandedView();
    } catch (error) {
      console.error(`Error deleting post ${postId}:`, error);
    }
  };

  const onUpdatePost = async (postId) => {
    setUpdatePostId(postId);
    setIsExpandedEditing(true);
    setIsExpandedPost(false);
  };

  const handleUpdatePost = async (postId) => {
    try {
      const response = await postService.getPost(postId);
      setPostToEdit(response.data);
      setIsExpandedEditing(true);
      setIsExpandedPost(false);
    } catch (error) {
      console.error(`Error fetching post ${postId} for update:`, error);
    }
  };

  const closeExpandedView = () => {
    // setIsExpandedEditing(false);
    // setIsExpandedCreate(false);
    setIsExpandedPost(false);  // setIsExpandedPost를 먼저 false로 변경
    // setSelectedPostId(null);
  };
  
  const addCategory = async (newCategory) => {
    try {
      console.log('카테고리 추가:', newCategory);
      loadCategories();
      setIsExpandedCategoryForm(false);
    } catch (error) {
      console.error('Error adding category:', error);
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
        {loginSession && (
          <div className='add-category-button'>
            <button onClick={() => setIsExpandedCategoryForm(!isExpandedCategoryForm)}>카테고리 등록하기</button>
          </div>
        )}
        {isExpandedCategoryForm && loginSession && (
          <div className='add-save-button'>
            <CategoryForm onAddCategory={addCategory} />
          </div>
        )}
      </aside>

      <main>
        <div className="posts-container">
          {loginSession && (
            <div>
              <div className='add-post-button'>
                <button onClick={() => setIsExpandedCreate(!isExpandedCreate)}>게시글 등록하기</button>
              </div>
            </div>
          )}
          {isExpandedCreate && loginSession && <PostForm categories={categories} className='post-form' />}
          {isExpandedEditing && loginSession 
          && (<UpdateForm postId={selectedPostId} className='post-form'></UpdateForm>)}
          {posts.map((post) => (
            <div
            key={post.id}
            className={`post-item ${selectedPostId === post.id && isExpandedPost ? 'expanded' : ''}`}
            onClick={(e) => { e.stopPropagation(); onSelectPost(post.id); }}>
            <h3>{post.title}</h3>
            {selectedPostId === post.id && isExpandedPost && (
              <div className="expanded-content">
                <button className="close-btn" onClick={(e) => { e.stopPropagation(); closeExpandedView(); }}>
                  Close
                </button>
                  {loginSession && (
                    <div>
                      <button className="delete-btn" onClick={() => onDeletePost(post.id)}>
                        삭제
                      </button>
                      <button className="update-btn" onClick={(e) => {
                        e.stopPropagation(); 
                        onUpdatePost(post.id);
                      }}>
                        수정
                      </button>
                    </div>
                  )}
                  {selectedPost && <p>{post.content}</p>}
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
