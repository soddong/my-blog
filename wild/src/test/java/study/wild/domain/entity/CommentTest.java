package study.wild.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("댓글을_빌더로_생성한다")
    public void whenUsingBuilder_thenCorrect() {
        Comment comment = Comment.builder()
                .content("댓글 내용")
                .build();

        entityManager.persistAndFlush(comment);

        assertThat(comment.getContent()).isEqualTo("댓글 내용");
        assertThat(comment.getCreatedDate()).isNotNull();
        assertThat(comment.getModifiedDate()).isNotNull();
    }
}