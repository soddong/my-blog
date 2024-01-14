package study.wild.dto;

import study.wild.domain.entity.Category;

public record CategoryDto(
        Long id,
        String name
) {
    public static CategoryDto from(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public Category toEntity() {
        return new Category(id, name);
    }
}
