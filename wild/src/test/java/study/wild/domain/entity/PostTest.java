package study.wild.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("게시글을_빌더로_생성한다")
    public void whenUsingBuilder_thenCorrect() {
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        entityManager.persistAndFlush(post);

        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        
        assertThat(post.getCreatedDate()).isNotNull();
        assertThat(post.getModifiedDate()).isNotNull();
    }

    @Test
    public void whenViewIncreased_thenViewCountCorrect() {
        Post post = new Post();
        post.increaseView();
        assertThat(post.getView()).isEqualTo(1);
    }

}