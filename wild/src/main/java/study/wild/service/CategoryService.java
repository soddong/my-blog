package study.wild.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.Category;
import study.wild.dto.CategoryDto;
import study.wild.repository.CategoryRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryDto.toEntity());
        return CategoryDto.from(category);
    }
    
}
