package study.wild.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category defaultCategory() {
        return Category.builder().
                id(1L)
                .name("")
                .build();
    }

}
