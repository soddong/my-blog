package study.wild.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.CategoryDto;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void 카테고리_등록_테스트() {
        // given
        CategoryDto categoryDto = createCategoryDto("기록");

        // when
        CategoryDto saveCategory = categoryService.createCategory(categoryDto);

        // then
        assertThat(saveCategory.name()).isEqualTo(categoryDto.name());

    }

    private CategoryDto createCategoryDto(String name) {
        return new CategoryDto(null, name);
    }
}