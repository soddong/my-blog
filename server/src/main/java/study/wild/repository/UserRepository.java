package study.wild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.wild.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
