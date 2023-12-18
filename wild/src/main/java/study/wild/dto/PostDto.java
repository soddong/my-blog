package study.wild.dto;

import study.wild.domain.Category;
import study.wild.domain.Post;

public record PostDto(
        Long id,
        Long categoryId,
        String title,
        String content
) {
    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getCategory().getId(),
                post.getTitle(), post.getContent());
    }

    public Post toEntity(Category category) {
        return Post.builder()
                .id(this.id)
                .category(category)
                .title(this.title)
                .content(this.content)
                .build();
    }
}

