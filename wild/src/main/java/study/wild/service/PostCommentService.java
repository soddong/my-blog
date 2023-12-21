package study.wild.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.entity.Comment;
import study.wild.dto.CommentDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommentService {

    private final CommentService commentService;

    private final PostService postService;

    /**
     * 특정 게시글에 댓글 등록
     */
    @Transactional
    public CommentDto createCommentWithPost(Long postId, CommentDto contentDto) {
        Comment comment = new Comment();
        comment.setPost(postService.getPost(postId, false));
        comment.setContent(contentDto.content());
        return commentService.saveComment(comment);
    }

    /**
     * 게시글과 관련 댓글 삭제
     */
    @Transactional
    public void deletePostWithComment(Long postId) {
        postService.deletePostById(postId);
        commentService.deleteCommentByPostId(postId);
    }

}
