package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.CategoryDto;
import study.wild.dto.UserDto;
import study.wild.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        // given
        UserDto userDto = new UserDto(1L, "gus9300", "tvxq1226@");

        // when
        UserDto createdUser = userService.createUser(userDto);

        // then
        Assertions.assertThat(createdUser.getUserId())
                .isEqualTo(userDto.getUserId());
    }

    @Test
    void deleteUser() {
        // given
        UserDto createdUser = userService.createUser(new UserDto(1L, "gus9300", "tvxq1226@"));

        // when
        userService.deleteUser(createdUser.getUserId());

        // then
        assertThatThrownBy(() ->
                userService.findUser(createdUser.getUserId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User not found");;
    }
}