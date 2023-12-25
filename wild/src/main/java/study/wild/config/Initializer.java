package study.wild.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import study.wild.domain.entity.Category;
import study.wild.repository.CategoryRepository;

@Component
public class Initializer implements CommandLineRunner {
    public static final Long INITIAL_CATEGORY_ID = 1L;
    public static final String INITIAL_CATEGORY_NAME = "";

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        categoryRepository.findById(INITIAL_CATEGORY_ID).orElseGet(() -> {
            Category defaultCategory = new Category(INITIAL_CATEGORY_ID, INITIAL_CATEGORY_NAME);
            return categoryRepository.save(defaultCategory);
        });
    }
}

