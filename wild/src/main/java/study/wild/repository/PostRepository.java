package study.wild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.wild.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
