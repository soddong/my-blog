package study.wild.dto;

import study.wild.domain.entity.Comment;

public record CommentDto(
        Long id,
        String content
) {
    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getContent());
    }

    public Comment toEntity() {
        return Comment.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }
}