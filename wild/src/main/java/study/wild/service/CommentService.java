package study.wild.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.exception.CommentNotFoundException;
import study.wild.domain.entity.Comment;
import study.wild.dto.CommentDto;
import study.wild.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     */
    @Transactional
    public CommentDto saveComment(Comment comment) {
        return CommentDto.from(commentRepository.save(comment));
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentDto editComment(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.setContent(commentDto.content());
        return CommentDto.from(comment);
    }

    /**
     * 전체 댓글 조회
     */
    public List<CommentDto> getCommentAll() {
        return commentRepository.findAll()
                .stream()
                .map(CommentDto::from)
                .collect(Collectors.toList());
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

    /**
     * 특정 게시글에 포함된 댓글들 삭제
     */
    @Transactional
    public void deleteCommentByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
    }
}