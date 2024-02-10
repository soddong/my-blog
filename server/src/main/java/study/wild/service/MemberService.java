package study.wild.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.exception.DuplicatedUserException;
import study.wild.domain.entity.Member;
import study.wild.domain.entity.Role;
import study.wild.dto.MemberDto;
import study.wild.repository.MemberRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findUserByName(username)
                .orElse(null);
        return User.builder().username(user.getName()).password(user.getPassword()).roles(Role.USER.toString()).build();
    }

    public boolean login(MemberDto memberDto) {
        Member user = memberRepository.findUserByName(memberDto.getName())
                .orElse(null);
        return user != null && passwordEncoder.matches(memberDto.getPassword(), user.getPassword());
    }

    @Transactional
    public boolean join(MemberDto memberDto) {
        Optional<Member> member = memberRepository.findUserByName(memberDto.getName());
        if (member.isPresent()) {
            return false;
        }
        Member newMember = memberDto.toEntity(passwordEncoder);
        memberRepository.save(newMember);
        return true;
    }
}
