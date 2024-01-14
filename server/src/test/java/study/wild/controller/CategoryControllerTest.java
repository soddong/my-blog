package study.wild.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import study.wild.dto.CategoryDto;
import study.wild.service.CategoryService;
import study.wild.service.PostCategoryService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private PostCategoryService postCategoryService;

    @Test
    @DisplayName("카테고리를 등록한다")
    void saveCategoryTest() throws Exception {
        // given
        CategoryDto category = new CategoryDto(null, "공부");
        CategoryDto returnedCategory = new CategoryDto(1L, "공부");
        given(categoryService.createCategory(any(CategoryDto.class))).willReturn(returnedCategory);

        String categoryJson = objectMapper.writeValueAsString(category);

        // when & then
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("공부"));
    }

    @Test
    @DisplayName("카테고리를 수정한다")
    void updateCategoryTest() throws Exception {
        // given
        Long categoryId = 1L;
        CategoryDto originalCategory = new CategoryDto(categoryId, "공부");
        CategoryDto updatedCategory = new CategoryDto(categoryId, "새 카테고리");

        given(categoryService.updateCategory(eq(categoryId), any(CategoryDto.class))).willReturn(updatedCategory);

        String categoryJson = objectMapper.writeValueAsString(originalCategory);

        // when & then
        mockMvc.perform(put("/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath("$.name").value("새 카테고리"));
    }

    @Test
    @DisplayName("모든 카테고리를 조회한다")
    void getCategoriesTest() throws Exception {
        // given
        List<CategoryDto> categoryList = Arrays.asList(
                new CategoryDto(1L, "카테고리1"),
                new CategoryDto(2L, "카테고리2")
        );
        given(categoryService.getCategoryAll()).willReturn(categoryList);

        // when & then
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("카테고리1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("카테고리2"));
    }

    @Test
    @DisplayName("카테고리를 삭제한다")
    void deleteCategoryTest() throws Exception {
        // given
        Long categoryId = 1L;
        willDoNothing().given(postCategoryService).deleteCategoryWithValidation(categoryId);

        // when & then
        mockMvc.perform(delete("/categories/" + categoryId))
                .andExpect(status().isOk());

        verify(postCategoryService).deleteCategoryWithValidation(categoryId);
    }
}
