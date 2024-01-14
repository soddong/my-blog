import React, { useEffect, useState } from 'react';
import { categoryService } from '../../service/categoryService'; // categoryService import
import { postService } from '../../service/postService'; // postService import
import '../../css/style.css'; // CSS 파일 경로를 적절하게 조정하세요.

const Posts = () => {
  const [categories, setCategories] = useState([]);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    loadCategories();
    loadPosts();
  }, []);

  const loadCategories = async () => {
    const response = await categoryService.getCategories();
    setCategories(response.data);
  };

  const loadPosts = async () => {
    const response = await postService.getPosts();
    setPosts(response.data);
  };

  return (
    <div>
      <aside>
        <h2>Category</h2>
        <ul id="category-list">
          {categories.map(category => (
            <li key={category.id}>{category.name}</li>
          ))}
        </ul>
      </aside>

      <main>
        {posts.map(post => (
          <div key={post.id}>
            <h3>{post.title}</h3>
            <p>{post.content}</p>
            {/* 포스트의 나머지 내용을 여기에 렌더링합니다. */}
          </div>
        ))}
      </main>
    </div>
  );
};

export default Posts;


// import React, { useEffect, useState } from 'react';
// import '../../css/style.css'; // CSS 파일 경로를 적절하게 조정하세요.

// const Posts = () => {
//   const [categories, setCategories] = useState([]);
//   // posts 상태도 필요한 경우 추가할 수 있습니다.

//   useEffect(() => {
//     // 여기에서 카테고리 데이터를 불러오는 로직을 구현합니다.
//     // 예를 들어, categories.js의 기능을 여기에 통합할 수 있습니다.
//     loadCategories();
//   }, []);

//   const loadCategories = async () => {
//     // 카테고리 데이터를 불러오는 API 호출 로직을 여기에 구현합니다.
//     // 예시: setCategories(await fetchCategories());
//   };

//   return (
//     <div>
//       <aside>
//         <h2>Category</h2>
//         <ul id="category-list">
//           {/* 여기에 카테고리 목록을 동적으로 렌더링합니다. */}
//           {categories.map(category => (
//             <li key={category.id}>{category.name}</li>
//           ))}
//         </ul>
//       </aside>

//       <main>
//         {/* 여기에 메인 컨텐츠 (게시글 목록 등)를 렌더링합니다. */}
//       </main>
//     </div>
//   );
// };

// export default Posts;

// // App.js
// import React, { useState, useEffect } from 'react';
// import CategoryForm from './components/CategoryForm';
// import CategoryList from './components/CategoryList';
// import PostForm from './components/PostForm';
// import PostList from './components/PostList';
// import PostDetail from './components/PostDetail';
// import { categoryService } from './service/categoryService';
// import { postService } from './service/postService';
// import './App.css';

// function App() {
//     const [categories, setCategories] = useState([]);
//     const [posts, setPosts] = useState([]);
//     const [selectedPost, setSelectedPost] = useState(null);
//     const [selectedCategory, setSelectedCategory] = useState(null);
//     const [isEditing, setIsEditing] = useState(false);

//     useEffect(() => {
//         loadCategories();
//         loadPosts();
//     }, []);

//     const loadCategories = async () => {
//         const response = await categoryService.getCategories();
//         setCategories(response.data);
//     };

//     const loadPosts = async (categoryId = null) => {
//         const response = await postService.getPosts(categoryId);
//         setPosts(response.data);
//     };

//     const handleAddPost = async (post) => {
//         await postService.createPost(post);
//         loadPosts();
//     };

//     const handleSelectPost = async (postId) => {
//         const response = await postService.getPost(postId);
//         setSelectedPost(response.data);
//     };

//     const handleBackToList = () => {
//         setSelectedPost(null);
//     };

//     const handleAddCategory = async (category) => {
//         if (category.id) {
//             await categoryService.updateCategory(category.id, category);
//         } else {
//             await categoryService.createCategory(category);
//         }
//         loadCategories();
//         setSelectedCategory(null);
//         setIsEditing(false);
//     };

//     const handleDeleteCategory = async (categoryId) => {
//         await categoryService.deleteCategory(categoryId);
//         loadCategories();
//     };

//     const handleEditCategory = (category) => {
//         setSelectedCategory(category);
//         setIsEditing(true);
//     };

//     const handleUpdateCategory = async (category) => {
//         await categoryService.updateCategory(selectedCategory.id, category);
//         loadCategories();
//         setSelectedCategory(null);
//         setIsEditing(false);
//     };

//     return (
//         <div className="App">
//             <header className="App-header">
//                 <h1>Category & Post Management</h1>
//             </header>
//             <CategoryForm
//                 onAddCategory={handleAddCategory}
//                 onUpdateCategory={handleUpdateCategory} // 추가: 수정 버튼 클릭 시 호출될 함수
//                 selectedCategory={selectedCategory}
//                 isEditing={isEditing}
//             />
//             <CategoryList
//                 categories={categories}
//                 onEdit={handleEditCategory}
//                 onDelete={handleDeleteCategory}
//                 onUpdate={handleUpdateCategory}
//             />
//             <PostForm onAddPost={handleAddPost} categories={categories} />
//             {selectedPost ? (
//                 <PostDetail post={selectedPost} onBack={handleBackToList} />
//             ) : (
//                 <PostList posts={posts} onSelectPost={handleSelectPost} />
//             )}
//         </div>
//     );
// }

// export default App;