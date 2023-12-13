package study.wild.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.PostDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    public void 게시글_등록_테스트() {
        // given
        PostDto postDto = createPostDto("게시물A", "내용A");

        // when
        PostDto savedPostDto = postService.savePost(postDto);

        // then
        assertThat(savedPostDto).isNotNull();
        assertThat(savedPostDto.title()).isEqualTo("게시물A");
        assertThat(savedPostDto.content()).isEqualTo("내용A");
    }

    @Test
    public void 전체_게시글_조회_테스트() {
        // given
        createAndSavePostDto("게시글A", "내용A");
        createAndSavePostDto("게시글B", "내용B");
        createAndSavePostDto("게시글C", "내용C");

        // when
        List<PostDto> postDtos = postService.findPosts();

        // then
        assertThat(postDtos).hasSize(3)
                .extracting(PostDto::title)
                .containsExactly("게시글A", "게시글B", "게시글C");
    }

    @Test
    public void 특정_게시글_조회_테스트() {
        // given
        PostDto postDto = createPostDto("게시물A", "내용A");

        // when
        PostDto findedPostDto = postService.findPost(postService.savePost(postDto).id());

        // then
        assertThat(findedPostDto.title()).isEqualTo("게시물A");
        assertThat(findedPostDto.content()).isEqualTo("내용A");
    }

    @Test
    public void 게시글_수정_테스트() {
        // given
        PostDto originalPost = createPostDto("원래 제목", "원래 내용");
        PostDto savedPost = postService.savePost(originalPost);

        PostDto updatedPostDto = createPostDto("수정된 제목", "수정된 내용");

        // when
        PostDto updatedPost = postService.updatePost(savedPost.id(), updatedPostDto);

        // then
        assertThat(updatedPost.title()).isEqualTo("수정된 제목");
        assertThat(updatedPost.content()).isEqualTo("수정된 내용");
    }

    private void createAndSavePostDto(String title, String content) {
        PostDto postDto = createPostDto(title, content);
        postService.savePost(postDto);
    }

    private PostDto createPostDto(String title, String content) {
        return new PostDto(null, title, content);
    }
}
