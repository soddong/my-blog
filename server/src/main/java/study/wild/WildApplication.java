package study.wild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WildApplication {

    public static void main(String[] args) {
        SpringApplication.run(WildApplication.class, args);
    }

}