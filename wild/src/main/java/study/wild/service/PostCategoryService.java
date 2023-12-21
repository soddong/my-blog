package study.wild.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.exception.NonEmptyCategoryException;
import study.wild.dto.CategoryDto;
import study.wild.dto.PostDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCategoryService {

    private final CategoryService categoryService;

    private final PostService postService;

    /**
     * 특정 카테고리에 게시글 등록
     */
    public PostDto createPostWithCategory(PostDto postDto) {
        CategoryDto categoryDto = categoryService.getCategoryByPost(postDto);
        return postService.createPost(postDto, categoryDto);
    }

    /**
     * 카테고리 삭제 (카테고리에 게시글이 없을 경우에만 삭제 가능)
     */
    public void deleteCategoryWithValidation(Long categoryId) {
        if (postService.hasPostInCategory(categoryId)) {
            throw new NonEmptyCategoryException();
        }
        categoryService.deleteCategory(categoryId);
    }
}
