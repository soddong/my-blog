package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.Category;
import study.wild.dto.CategoryDto;
import study.wild.dto.PostDto;
import study.wild.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryDto.toEntity());
        return CategoryDto.from(category);
    }

    @Transactional
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(categoryDto.name());
        return CategoryDto.from(categoryRepository.save(category));
    }

    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::from)
                .collect(Collectors.toList());
    }

    public CategoryDto findByPost(PostDto postDto) {
        if (postDto.categoryId() == null) {
            return CategoryDto.defaultCategory();
        }
        return CategoryDto.from(
                categoryRepository.findById(postDto.categoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found " + postDto.categoryId()))
        );
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
