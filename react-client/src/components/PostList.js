import React from 'react';

const PostList = ({ posts, onSelectPost }) => {
    return (
        <div>
            {posts.map(post => (
                <div key={post.id} onClick={() => onSelectPost(post.id)}>
                    {post.title}
                </div>
            ))}
        </div>
    );
};

export default PostList;
