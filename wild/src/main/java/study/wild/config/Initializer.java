package study.wild.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import study.wild.domain.Category;
import study.wild.repository.CategoryRepository;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        categoryRepository.findById(1L).orElseGet(() -> {
            Category defaultCategory = new Category(1L, "");
            return categoryRepository.save(defaultCategory);
        });
    }
}

