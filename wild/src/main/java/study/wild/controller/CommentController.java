package study.wild.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import study.wild.dto.CommentDto;
import study.wild.service.CommentService;
import study.wild.service.PostCommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final PostCommentService postCommentService;

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto save(@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto) {
        return postCommentService.createCommentWithPost(postId, commentDto);
    }

    @PutMapping("/comments/{commentId}")
    public CommentDto updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDto commentDto) {
        return commentService.editComment(commentId, commentDto);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPost(@PathVariable("postId") Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}
