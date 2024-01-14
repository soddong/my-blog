package study.wild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.wild.domain.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
