package com.kbeauty.myapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kbeauty.myapp.entity.Member;
import com.kbeauty.myapp.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpSession session) {
        if (isBlank(request.email()) || isBlank(request.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일과 비밀번호를 입력해주세요.");
        }

        Member member = memberRepository.findByEmail(request.email())
                .orElseGet(() -> memberRepository.save(new Member(
                        request.email(),
                        getDefaultName(request.email()),
                        request.password())));

        if (!member.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        String token = "member-" + member.getMemberId();
        LoginResponse response = new LoginResponse(member.getMemberId(), member.getEmail(), member.getName(), token);
        saveSession(session, response);
        return response;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse register(@RequestBody RegisterRequest request, HttpSession session) {
        if (isBlank(request.email()) || isBlank(request.password()) || isBlank(request.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원 정보를 모두 입력해주세요.");
        }

        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
        }

        Member member = memberRepository.save(new Member(request.email(), request.name(), request.password()));
        LoginResponse response = new LoginResponse(member.getMemberId(), member.getEmail(), member.getName(), "member-" + member.getMemberId());
        saveSession(session, response);
        return response;
    }

    @GetMapping("/me")
    public LoginResponse me(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        String email = (String) session.getAttribute("email");
        String name = (String) session.getAttribute("name");
        String token = (String) session.getAttribute("token");

        if (memberId == null || email == null || name == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        return new LoginResponse(memberId, email, name, token);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }

    private void saveSession(HttpSession session, LoginResponse response) {
        session.setAttribute("memberId", response.memberId());
        session.setAttribute("email", response.email());
        session.setAttribute("name", response.name());
        session.setAttribute("token", response.token());
    }

    private String getDefaultName(String email) {
        int atIndex = email.indexOf("@");
        return atIndex > 0 ? email.substring(0, atIndex) : "K-Glow Member";
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public record LoginRequest(String email, String password) {
    }

    public record RegisterRequest(String email, String name, String password) {
    }

    public record LoginResponse(Long memberId, String email, String name, String token) {
    }
}
