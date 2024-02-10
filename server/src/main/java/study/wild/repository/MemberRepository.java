package study.wild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.wild.domain.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findUserByName(String name);
}
