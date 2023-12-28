package study.wild.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static study.wild.config.Initializer.INITIAL_CATEGORY_ID;
import static study.wild.config.Initializer.INITIAL_CATEGORY_NAME;

public class CategoryTest {

    @Test
    @DisplayName("카테고리를_생성한다")
    public void whenCategoryCreated_thenCorrect() {
        Category category = new Category();
        category.setId(1L);
        category.setName("공부");

        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("공부");
    }

    @Test
    @DisplayName("카테고리를_빌더로_생성한다")
    public void whenUsingBuilder_thenCorrect() {
        Category category = Category.builder()
                .id(1L)
                .name("공부")
                .build();

        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("공부");
    }

    @Test
    @DisplayName("Default 카테고리를 얻을 수 있다")
    public void whenUsingDefaultCategory_thenCorrect() {
        Category category = Category.defaultCategory();

        assertThat(category.getId()).isEqualTo(INITIAL_CATEGORY_ID);
        assertThat(category.getName()).isEqualTo(INITIAL_CATEGORY_NAME);
    }
}