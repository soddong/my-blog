import React from 'react';

const PostDetail = ({ post, onBack }) => {
    return (
        <div>
            <h3>{post.title}</h3>
            <p>{post.content}</p>
            <button onClick={onBack}>목록으로 돌아가기</button>
        </div>
    );
};

export default PostDetail;
