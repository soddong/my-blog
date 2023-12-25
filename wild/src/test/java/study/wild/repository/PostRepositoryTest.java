package study.wild.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.wild.domain.entity.Category;
import study.wild.domain.entity.Post;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;
    private Long id = 1L;

    @BeforeEach
    void init() {
        category = categoryRepository.save(
                Category.builder()
                        .name("테스트")
                        .build()
        );
    }

    @Test
    public void saveTest() {
        // given
        Post post = createPost("Title", "Content");

        // when
        Post savedPost = postRepository.save(post);

        // then
        assertThat(savedPost.getId()).isEqualTo(post.getId());
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(post.getContent());
    }

    @Test
    public void findPostByIdAndIsDeletedTest() {
        // given
        Post post = createPost("Title", "Content");
        postRepository.save(post);

        // when
        Optional<Post> savedPostNotDeleted = postRepository.findPostByIdAndIsDeleted(post.getId(), false);
        Optional<Post> postByIdAndDeleted = postRepository.findPostByIdAndIsDeleted(post.getId(), true);

        // then
        assertDoesNotThrow(savedPostNotDeleted::get);
        assertThat(savedPostNotDeleted.get().getId()).isEqualTo(post.getId());
        assertTrue(postByIdAndDeleted.isEmpty());
    }

    @Test
    public void findAllByAndDeletedTest() {
        // given
        Post post1 = createPost("Title1", "Content1");
        Post post2 = createPost("Title2", "Content2");
        Post post3 = createPost("Title3", "Content3");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        // when
        List<Post> savedPostNotDeleted = postRepository.findAllByAndDeleted(false);
        List<Post> postByIdAndDeleted = postRepository.findAllByAndDeleted(true);

        // then
        assertThat(savedPostNotDeleted).hasSize(3)
                .extracting(Post::getTitle)
                .containsExactly("Title1", "Title2", "Title3");
        assertThat(postByIdAndDeleted).hasSize(0);
    }

    @Test
    public void findPostByCategoryIdAndDeletedTest() {
        // given
        Post post1 = createPost("Title1", "Content1");
        Post post2 = createPost("Title2", "Content2");
        postRepository.save(post1);
        postRepository.save(post2);

        // when
        List<Post> postsByCategory = postRepository.findPostByCategoryIdAndDeleted(category.getId(), false);

        // then
        assertThat(postsByCategory).hasSize(2)
                .extracting(Post::getTitle)
                .containsExactly("Title1", "Title2");
    }

    private Post createPost(String title, String content) {
        return Post.builder()
                .id(id++)
                .title(title)
                .content(content)
                .category(getTestCategory())
                .build();
    }

    private Category getTestCategory() {
        return categoryRepository.findById(category.getId()).get();
    }
}