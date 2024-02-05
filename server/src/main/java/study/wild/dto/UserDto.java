package study.wild.dto;

import lombok.*;
import study.wild.domain.entity.Category;
import study.wild.domain.entity.Post;
import study.wild.domain.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String name;
    private String password;

    public static UserDto from(User user) {
        return new UserDto(user.getUserId(),
                user.getName(),
                user.getPassword());
    }

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .name(this.name)
                .password(this.password)
                .build();
    }

}
