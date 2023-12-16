package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.Comment;
import study.wild.dto.CommentDto;
import study.wild.dto.PostDto;
import study.wild.repository.CommentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Test
    public void 댓글_등록_테스트() {
        // given
        CommentDto commentDto = createCommentDto("댓글 내용");
        Long postId = createPostDtoAndGetId("제목", "내용");

        // when
        CommentDto saveComment = commentService.saveComment(postId, commentDto);

        // then
        assertThat(saveComment.content()).isEqualTo(commentDto.content());
    }

    @Test
    public void 존재하지_않는_게시글에_댓글등록시_예외발생() {
        // given
        CommentDto commentDto = createCommentDto("댓글 내용");

        // when & then
        assertThatThrownBy(() -> commentService.saveComment(5L, commentDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Post not found");
    }

    private Long createPostDtoAndGetId(String title, String content) {
        PostDto postDto = new PostDto(null, title, content);
        return postService.savePost(postDto).id();
    }

    private CommentDto createCommentDto(String content) {
        return new CommentDto(content);
    }
}