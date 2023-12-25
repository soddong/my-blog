package study.wild.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.wild.domain.common.BaseTimeEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(Long id, String content, Post post) {
        this.id = id;
        this.content = content;
        this.post = post;
    }
}
