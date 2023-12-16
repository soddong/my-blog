package study.wild.dto;

import study.wild.domain.Comment;

public record CommentDto(
        String content
) {
    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getContent());
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(this.content)
                .build();
    }
}