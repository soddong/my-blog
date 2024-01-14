package study.wild.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.wild.domain.entity.Comment;
import study.wild.domain.entity.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    void init() {
        post = postRepository.save(
                Post.builder()
                        .title("Title")
                        .content("Content")
                        .build()
        );
        System.out.println("--------------test--------------");
        System.out.println(postRepository.findAll().size());
    }

    @Test
    public void saveTest() {
        // given
        Comment comment = createComment("content");

        // when
        Comment savedComment = commentRepository.save(comment);

        // then
        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getContent()).isEqualTo(comment.getContent());
    }

    @Test
    public void findByPostIdTest() {
        // given
        Comment comment = createComment("content");
        Comment savedComment = commentRepository.save(comment);

        // when
        List<Comment> findComments = commentRepository.findByPostId(post.getId());

        // then
        assertThat(findComments).hasSize(1);
    }

    @Test
    public void deleteByPostIdTest() {
        // given
        Comment comment1 = createComment("content");
        Comment comment2 = createComment("content");
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        commentRepository.deleteByPostId(post.getId());

        // then
        assertThat(commentRepository.findByPostId(post.getId())).hasSize(0);
    }

    private Comment createComment(String text) {
        return Comment.builder()
                .content(text)
                .post(getPost())
                .build();
    }

    private Post getPost() {
        return postRepository.findById(post.getId()).get();
    }
}