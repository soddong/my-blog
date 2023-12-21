package study.wild.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.CommentDto;
import study.wild.dto.PostDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostCommentServiceTest {

    @Autowired
    PostCommentService postCommentService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @Autowired
    PostCategoryService postCategoryService;

    @Test
    void 게시글_삭제시_연관댓글_삭제() {
        // given
        Long postId = createPostDtoAndGetId("제목", "내용");
        for (int i = 0; i < 4; i++) {
            saveAndGetCommentId(postId, "댓글" + i);
        }

        // when
        postCommentService.deletePostWithComment(postId);

        // then
        assertThat(commentService.getCommentAll())
                .hasSize(0);
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