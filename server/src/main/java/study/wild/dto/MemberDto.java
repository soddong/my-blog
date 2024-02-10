package study.wild.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.wild.domain.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private String name;
    private String password;

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getName(),
                member.getPassword());
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(this.name)
                .password(passwordEncoder.encode(this.password))
                .build();
    }

}
