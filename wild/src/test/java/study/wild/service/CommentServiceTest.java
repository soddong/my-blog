package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.CommentDto;
import study.wild.dto.PostDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private PostCommentService postCommentService;

    @Test
    public void 댓글_등록_테스트() {
        // given
        Long postId = createPostDtoAndGetId("제목", "내용");
        CommentDto commentDto = createCommentDto("댓글 내용");

        // when
        CommentDto saveComment = postCommentService.createCommentWithPost(postId, commentDto);

        // then
        assertThat(saveComment.content()).isEqualTo(commentDto.content());
    }

    @Test
    public void 존재하지_않는_게시글에_댓글등록시_예외발생() {
        // given
        CommentDto commentDto = createCommentDto("댓글 내용");

        // when & then
        assertThatThrownBy(() -> postCommentService.createCommentWithPost(5L, commentDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Post not found");
    }

    @Test
    public void 댓글_수정_테스트() {
        // given
        Long commentId = saveAndGetCommentId(
                createPostDtoAndGetId("제목", "내용"),
                "댓글 내용"
        );
        CommentDto commentDto = createCommentDto("댓글 내용 수정");

        // when
        CommentDto updateCommentDto = commentService.editComment(commentId, commentDto);

        // then
        assertThat(updateCommentDto.content()).isEqualTo(commentDto.content());
    }

    @Test
    public void 특정_게시글의_댓글_조회_테스트() {
        // given
        Long postId = createPostDtoAndGetId("제목", "내용");
        postCommentService.createCommentWithPost(postId, createCommentDto("댓글1"));
        postCommentService.createCommentWithPost(postId, createCommentDto("댓글2"));

        // when & then
        assertThat(commentService.getCommentsByPost(postId))
                .hasSize(2)
                .extracting(CommentDto::content)
                .containsExactly("댓글1", "댓글2");
    }

    @Test
    public void 댓글_삭제_테스트() {
        // given
        Long postId = createPostDtoAndGetId("제목", "내용");
        Long commentId = saveAndGetCommentId(postId, "댓글");

        // when
        commentService.deleteComment(commentId);

        // then
        assertThat(commentService.getCommentsByPost(postId))
                .hasSize(0);
    }

    @Test
    public void 등록되지_않은_댓글_수정_시도시_에러발생() {
        assertThatThrownBy(() -> commentService.editComment(1L, createCommentDto("댓글 내용")));
    }

    private Long createPostDtoAndGetId(String title, String content) {
        PostDto postDto = new PostDto(null, null, title, content);
        return postCategoryService.createPostWithCategory(postDto).id();
    }

    private CommentDto createCommentDto(String content) {
        return new CommentDto(null, content);
    }

    private Long saveAndGetCommentId(Long postId, String content) {
        CommentDto commentDto = postCommentService.createCommentWithPost(
                postId,
                createCommentDto(content));
        return commentDto.id();
    }
}