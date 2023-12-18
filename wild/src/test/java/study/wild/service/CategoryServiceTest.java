package study.wild.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.CategoryDto;

import java.util.List;

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

    @Test
    public void 게시글_수정_테스트() {
        // given
        CategoryDto savedCategory = categoryService.createCategory(createCategoryDto("기록"));

        // when
        CategoryDto updatedCategory = categoryService.updateCategory(
                savedCategory.id(),
                createCategoryDto("공부")
        );

        // then
        assertThat(updatedCategory.name()).isEqualTo("공부");
    }

    @Test
    public void 전체_게시글_조회_테스트() {
        // given
        createAndSaveCategoryDto("공부");
        createAndSaveCategoryDto("기록");
        createAndSaveCategoryDto("여행");

        // when
        List<CategoryDto> categories = categoryService.findAllCategories();

        // then
        assertThat(categories).hasSize(3)
                .extracting(CategoryDto::name)
                .containsExactly("공부", "기록", "여행");
    }

    private CategoryDto createCategoryDto(String name) {
        return new CategoryDto(null, name);
    }

    private Long createAndSaveCategoryDto(String name) {
        CategoryDto categoryDto = createCategoryDto(name);
        return categoryService.createCategory(categoryDto).id();
    }
}