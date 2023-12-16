package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.Comment;
import study.wild.dto.CommentDto;
import study.wild.repository.CommentRepository;
import study.wild.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 등록
     */
    @Transactional
    public CommentDto saveComment(Long postId, CommentDto contentDto) {
        Comment comment = new Comment();
        comment.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found")));
        comment.setContent(contentDto.content());
        return CommentDto.from(commentRepository.save(comment));
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentDto updateComment(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        comment.setContent(commentDto.content());
        return CommentDto.from(commentRepository.save(comment));
    }

    /**
     * 특정 게시글의 댓글들 조회
     */
    public List<CommentDto> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(CommentDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}