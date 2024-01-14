import axios from 'axios';

const API_URL = 'http://localhost:8080/categories';

const createCategory = category => axios.post(API_URL, category);

const updateCategory = (categoryId, category) => axios.put(`${API_URL}/${categoryId}`, category);

const deleteCategory = categoryId => axios.delete(`${API_URL}/${categoryId}`);

const getCategories = () => axios.get(API_URL);

export const categoryService = {
    createCategory,
    updateCategory,
    deleteCategory,
    getCategories
};
