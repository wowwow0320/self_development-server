package project.self_development.routine.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.self_development.routine.dto.UserLoginRequestDto;
import project.self_development.routine.dto.UserResponseDto;
import project.self_development.routine.dto.UserSignupRequestDto;
import project.self_development.routine.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequestDto dto) {
        UserResponseDto userResponseDto = userService.signup(dto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDto dto, HttpSession session) {
        UserResponseDto userResponseDto = userService.login(dto, session);
        if (userResponseDto == null) {
            return ResponseEntity.status(400).body("로그인에 실패했습니다.");
        }
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 했습니다.");
    }
}

