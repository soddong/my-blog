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
}