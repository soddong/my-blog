package study.wild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.wild.domain.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}