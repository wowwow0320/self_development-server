package project.self_development.routine.controller;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<?> signup(@RequestBody UserSignupRequestDto dto) {
        if(dto.getName() == null || dto.getName().trim().isEmpty()){
            return ResponseEntity.status(400).body("이름을 입력해주세요.");
        }
        else if(dto.getEmail() == null|| dto.getEmail().trim().isEmpty()){
            return ResponseEntity.status(400).body("이메일을 입력해주세요.");
        }
        else if(dto.getPassword() == null || dto.getPassword().trim().isEmpty()){
            return ResponseEntity.status(400).body("비밀 번호를 입력해주세요.");
        }
        try {
            UserResponseDto userResponseDto = userService.signup(dto);
            return ResponseEntity.ok(userResponseDto);
        } catch (IllegalArgumentException e) {
            // service에서 던진 예외 메시지를 그대로 프론트에 전달
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 기타 예상치 못한 예외 처리
            return ResponseEntity.status(500).body("서버 에러가 발생했습니다.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto dto, HttpSession session) {
        if(dto.getEmail() == null || dto.getEmail().trim().isEmpty()){
            return ResponseEntity.status(400).body("이메일을 입력해주세요.");
        }
        else if(dto.getPassword() == null || dto.getPassword().trim().isEmpty()){
            return ResponseEntity.status(400).body("비밀번호를 입력해주세요.");
        }
        try{
            UserResponseDto userResponseDto = userService.login(dto, session);
            if(userResponseDto == null){
                return  ResponseEntity.status(400).body("로그인에 실패했습니다.");
            }
            return ResponseEntity.ok(userResponseDto);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("서버 에러가 발생했습니다.");
        }

    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        try{
            session.invalidate();
            return ResponseEntity.ok("로그아웃 했습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("서버 에러가 발생했습니다.");
        }
    }
}

