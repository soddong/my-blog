package study.wild.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @GeneratedValue
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(Long userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }
}

