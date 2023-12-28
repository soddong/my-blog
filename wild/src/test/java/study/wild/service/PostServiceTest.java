package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.exception.PostNotFoundException;
import study.wild.domain.entity.Category;
import study.wild.dto.CategoryDto;
import study.wild.dto.PostDto;
import study.wild.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @Test
    public void 게시글_등록_테스트() {
        // given
        PostDto postDto = createPostDto("제목A", "내용A");

        // when
        PostDto savedPostDto = postService.createPost(postDto, CategoryDto.from(Category.defaultCategory()));

        // then
        assertThat(savedPostDto).isNotNull();
        assertThat(savedPostDto.title()).isEqualTo("제목A");
        assertThat(savedPostDto.content()).isEqualTo("내용A");
    }

    @Test
    public void 게시글의_제목은_1자이상_50자이하_여야함() {
        // given
        PostDto postDto = createPostDto("", "내용A");

        // when
        PostDto savedPostDto = postService.createPost(postDto, CategoryDto.from(Category.defaultCategory()));

        // then
        assertThat(savedPostDto).isNotNull();
        assertThat(savedPostDto.title()).isEqualTo("");
        assertThat(savedPostDto.content()).isEqualTo("내용A");
    }

    @Test
    public void 전체_게시글_조회_테스트() {
        // given
        createAndSavePostDto("제목A", "내용A");
        createAndSavePostDto("제목B", "내용B");
        createAndSavePostDto("제목C", "내용C");

        // when
        List<PostDto> postDtos = postService.viewPosts(false);

        // then
        assertThat(postDtos).hasSize(3)
                .extracting(PostDto::title)
                .containsExactly("제목A", "제목B", "제목C");
    }

    @Test
    public void 특정_게시글_조회_테스트() {
        // given & when
        PostDto findedPostDto = postService.viewPostDetail(createAndSavePostDto("원래 제목", "원래 내용"));

        // then
        assertThat(findedPostDto.title()).isEqualTo("원래 제목");
        assertThat(findedPostDto.content()).isEqualTo("원래 내용");
    }

    @Test
    public void 게시글_수정_테스트() {
        // given
        Long postId = createAndSavePostDto("원래 제목", "원래 내용");

        PostDto updatedPostDto = createPostDto("수정된 제목", "수정된 내용");

        // when
        PostDto updatedPost = postService.editPost(postId, updatedPostDto);

        // then
        assertThat(updatedPost.title()).isEqualTo("수정된 제목");
        assertThat(updatedPost.content()).isEqualTo("수정된 내용");
    }

    @Test
    public void 삭제된_게시글_수정시_예외발생() {
        // given
        Long postId = createAndSavePostDto("원래 제목", "원래 내용");
        postService.deletePostById(postId);

        // when & then
        PostDto updatedPostDto = createPostDto("수정된 제목", "수정된 내용");
        assertThatThrownBy(() -> postService.editPost(postId, updatedPostDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Post not found");
    }

    @Test
    public void 게시물_soft_삭제_테스트() {
        // given
        Long postId = createAndSavePostDto("제목Q", "내용Q");

        // when
        postService.deletePostById(postId);

        // then
        assertThat(postService.viewPosts(false)).hasSize(0);
        assertThat(postService.viewPosts(true)).hasSize(1);
        assertThatThrownBy(() -> postService.viewPostDetail(postId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Post not found");
    }

    @Test
    public void 게시글_조회시_조회수_증가_테스트() {
        // given
        Long postId = createAndSavePostDto("게시글", "내용");

        // when & then
        for (int i = 1; i < 5; i++) {
            postService.viewPostDetail(postId);
            assertThat(postRepository.findPostByIdAndIsDeleted(postId, false)
                    .get().getView())
                    .isEqualTo(i);

        }
    }

    @Test
    public void 등록되지않은_게시글_조회시_예외발생() {
        assertThatThrownBy(() -> postService.getPost(1L, false))
                .isInstanceOf(PostNotFoundException.class);
    }

    private Long createAndSavePostDto(String title, String content) {
        PostDto postDto = createPostDto(title, content);
        CategoryDto categoryDto = CategoryDto.from(Category.defaultCategory());
        return postService.createPost(postDto, categoryDto).id();
    }

    private PostDto createPostDto(String title, String content) {
        return new PostDto(null, null, title, content);
    }
}
