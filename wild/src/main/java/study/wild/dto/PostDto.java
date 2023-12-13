package study.wild.dto;

import study.wild.domain.Post;

public record PostDto(
        Long id,
        String title,
        String content
) {
    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent());
    }

    public Post toEntity() {
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .build();
    }
}

