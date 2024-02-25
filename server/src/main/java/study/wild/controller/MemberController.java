package study.wild.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> login(@RequestBody MemberDto memberDto, HttpSession session) {
        boolean loginSuccess = memberService.login(memberDto);
        if (loginSuccess) {
            session.setAttribute("loginMember", memberDto);
            return ResponseEntity.status(HttpStatus.OK).body("로그인 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패! 아이디 또는 비밀번호를 확인해주세요.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("로그아웃 성공!");
    }

    @GetMapping("/session")
    public ResponseEntity<String> checkSession(HttpSession session) {
        if (session.getAttribute("loginMember") != null) {
            return ResponseEntity.ok("사용자가 로그인 상태입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자가 로그인 상태가 아닙니다.");
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
