package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.wild.dto.MemberDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void createUser() {
        // given
        MemberDto memberDto = new MemberDto("gus9300", "tvxq1226@");

        // when
        boolean isjoined = memberService.join(memberDto);

        // then
        Assertions.assertThat(isjoined)
                .isEqualTo(true);
    }
}