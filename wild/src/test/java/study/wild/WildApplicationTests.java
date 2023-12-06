package study.wild;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.Hello;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class WildApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		Hello hello = new Hello();
		em.persist(hello);

		em.flush();
		em.clear();

		List<Hello> resultList = em.createQuery("select m from Hello m", Hello.class)
				.getResultList();
		for (Hello h : resultList) {
			System.out.println(h);
		}
	}

}
