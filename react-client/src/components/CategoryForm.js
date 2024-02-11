import React, { useState, useEffect } from 'react';
import { categoryService } from '../service/categoryService'; 

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

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (isEditing) {
                await categoryService.updateCategory(category.id, category);
                onUpdateCategory(category);
            } else {
                const response = await categoryService.createCategory(category);
                onAddCategory(response.data);
            }
            // 저장 후 초기화
            setCategory({ id: null, name: '' });
        } catch (error) {
            console.error('Error adding/updating category:', error);
        }
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
