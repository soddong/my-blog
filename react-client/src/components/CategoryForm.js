import React, { useState, useEffect } from 'react';

const CategoryForm = ({ onAddCategory, onUpdateCategory, categoryToEdit, isEditing }) => {
    const [category, setCategory] = useState({ id: null, name: '' });

    useEffect(() => {
        if (categoryToEdit) {
            setCategory(categoryToEdit);
        } else {
            setCategory({ id: null, name: '' });
        }
    }, [categoryToEdit, isEditing]);

    const handleChange = (e) => {
        setCategory({ ...category, name: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (isEditing) {
            onUpdateCategory(category);
        } else {
            onAddCategory(category);
        }
        // 저장 후 초기화
        setCategory({ id: null, name: '' });
    };

    return (
        <form onSubmit={handleSubmit}>
            <input 
                type="text"
                value={category.name}
                onChange={handleChange}
                placeholder="카테고리 이름"
            />
            <button type="submit">{isEditing ? 'Update' : 'Save'}</button>
        </form>
    );
};

export default CategoryForm;
