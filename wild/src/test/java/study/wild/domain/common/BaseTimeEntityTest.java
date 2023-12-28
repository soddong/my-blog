package study.wild.domain.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import study.wild.domain.entity.Comment;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BaseTimeEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Created Data가 올바르게 설정된다")
    public void whenEntityCreated_thenCreatedDateIsSet() {
        // given
        Comment comment = new Comment();
        comment.setContent("댓글 내용");

        // when
        entityManager.persistAndFlush(comment);

        // then
        assertThat(comment.getCreatedDate()).isNotNull();
        assertThat(comment.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("Modified Data가 올바르게 설정된다")
    public void whenEntityUpdated_thenModifiedDateIsSet() {
        // given
        Comment comment = new Comment();
        comment.setContent("댓글 내용");
        entityManager.persistAndFlush(comment);

        // when
        comment.setContent("새로운 댓글 내용");
        entityManager.persistAndFlush(comment);

        // then
        assertThat(comment.getModifiedDate()).isNotNull();
        assertThat(comment.getModifiedDate()).isAfterOrEqualTo(comment.getCreatedDate());
    }
}