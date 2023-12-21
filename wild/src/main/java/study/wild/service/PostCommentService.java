package study.wild.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommentService {

    private final CommentService commentService;

    private final PostService postService;

    /**
     * 게시글과 관련 댓글 삭제
     */
    @Transactional
    public void deletePost(Long postId) {
        postService.deletePostById(postId);
        commentService.deleteCommentByPostId(postId);
    }

}
