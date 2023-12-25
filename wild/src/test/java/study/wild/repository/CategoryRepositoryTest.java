package study.wild.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.wild.domain.entity.Category;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Long id = 2L;

    @Test
    public void saveTest() {
        // given
        Category category = createCategory("공부");

        // when
        Category savedCategory = categoryRepository.save(category);

        // then
        assertThat(savedCategory.getId()).isEqualTo(category.getId());
        assertThat(savedCategory.getName()).isEqualTo(category.getName());
    }

    @Test
    public void findByIdTest() {
        // given
        Category category = createCategory("공부");
        categoryRepository.save(category);

        // when
        Optional<Category> savedCategory = categoryRepository.findById(category.getId());

        // then
        assertDoesNotThrow(savedCategory::get);
        assertThat(savedCategory.get().getId()).isEqualTo(category.getId());
        assertThat(savedCategory.get().getName()).isEqualTo(category.getName());

    }

    @Test
    public void findAllTest() {
        // given
        Category category1 = createCategory("공부");
        Category category2 = createCategory("일기");
        Category category3 = createCategory("그외");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // when
        List<Category> savedCategorys = categoryRepository.findAll();

        // then
        assertThat(savedCategorys).hasSize(3)
                .extracting(Category::getName)
                .containsExactly("공부", "일기", "그외");
    }

    @Test
    public void deleteByIdTest() {
        // given
        Category category = createCategory("공부");
        categoryRepository.save(category);

        // when
        categoryRepository.deleteById(category.getId());

        // then
        assertThat(categoryRepository.findAll())
                .hasSize(0);
    }


    private Category createCategory(String name) {
        return Category.builder()
                .id(id++)
                .name(name)
                .build();
    }
}
