package study.wild.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import study.wild.dto.MemberDto;
import study.wild.service.MemberService;

@RestController
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> login(@RequestBody MemberDto memberDto) {
        boolean loginSuccess = memberService.login(memberDto);
        if (loginSuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("로그인 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패! 아이디 또는 비밀번호를 확인해주세요.");
        }
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> join(@RequestBody MemberDto memberDto) {
        boolean joinSuccess = memberService.join(memberDto);
        if (joinSuccess) {
            return ResponseEntity.ok("회원가입 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패! 이미 존재하는 아이디입니다.");
        }
    }
}
