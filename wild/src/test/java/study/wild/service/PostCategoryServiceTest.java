package study.wild.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.CategoryDto;
import study.wild.dto.PostDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostCategoryServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostCategoryService postCategoryService;

    @Test
    void 특정_카테고리내_게시글들_조회_테스트() {
        // given
        CategoryDto savedCategoryDto1 = categoryService.createCategory(new CategoryDto(null, "공부"));
        CategoryDto savedCategoryDto2 = categoryService.createCategory(new CategoryDto(null, "일기"));

        postCategoryService.createPostWithCategory(createPostDto("제목", savedCategoryDto1.id(), "내용"));
        postCategoryService.createPostWithCategory(createPostDto("제목1", savedCategoryDto1.id(), "내용"));
        postCategoryService.createPostWithCategory(createPostDto("제목2", savedCategoryDto2.id(), "내용"));

        // when
        List<PostDto> posts1 = postService.getPostsByCategory(savedCategoryDto1.id(), false);
        List<PostDto> posts2 = postService.getPostsByCategory(savedCategoryDto2.id(), false);

        // then
        assertThat(posts1).hasSize(2);
        assertThat(posts2).hasSize(1);
    }

    private PostDto createPostDto(String title, Long categoryId, String content) {
        return new PostDto(null, categoryId, title, content);
    }

    private PostDto createPostDto(String title, String content) {
        return new PostDto(null, null, title, content);
    }
}