import React from 'react';

const CategoryList = ({ categories, onEdit, onDelete }) => {
    return (
        <ul>
            {categories.map((category) => (
                <li key={category.id}>
                    {category.name}
                    <button onClick={() => onEdit(category)}>수정</button>
                    <button onClick={() => onDelete(category.id)}>삭제</button>
                </li>
            ))}
        </ul>
    );
};

export default CategoryList;