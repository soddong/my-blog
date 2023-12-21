package study.wild.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.exception.CategoryNotFoundException;
import study.wild.domain.entity.Category;
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

    /**
     * 카테고리 등록
     */
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryDto.toEntity());
        return CategoryDto.from(category);
    }

    /**
     * 카테고리 업데이트
     */
    @Transactional
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        category.setName(categoryDto.name());
        return CategoryDto.from(category);
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    /**
     * 전체 카테고리 조회
     */
    public List<CategoryDto> getCategoryAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 카테고리 조회
     */
    public CategoryDto getCategory(Long categoryId) {
        return CategoryDto.from(categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new));
    }

    /**
     * 게시글이 포함된 카테고리 찾기
     */
    public CategoryDto getCategoryByPost(PostDto postDto) {
        if (postDto.categoryId() == null) {
            return CategoryDto.from(Category.defaultCategory());
        }
        return getCategory(postDto.categoryId());
    }

}
