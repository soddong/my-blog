package study.wild.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.exception.UserNotFoundException;
import study.wild.domain.entity.User;
import study.wild.dto.UserDto;
import study.wild.repository.UserRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User newUser = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .build();
        return UserDto.from(userRepository.save(newUser));
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void findUser(Long userId) {
        userRepository.findById(userId);
    }

}
