package project.self_development.routine.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.self_development.routine.domain.EmailVerification;
import project.self_development.routine.dto.EmailDto;
import project.self_development.routine.service.MailService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class EmailController {
    private final MailService mailService;

    @PostMapping("/email-auth")
    public ResponseEntity<?> mailSend(@RequestBody EmailDto emailDto) throws MessagingException {
        if(emailDto.getEmail() == null || emailDto.getEmail().trim().isEmpty()){
            return ResponseEntity.status(400).body("이메일을 입력해주세요.");
        }
        mailService.sendMail(emailDto.getEmail());
        return ResponseEntity.ok("인증 코드 발송되었습니다.");
    }
    @PostMapping("/email-check")
    public ResponseEntity<?> verify(@RequestBody EmailDto emailDto) {
        if(emailDto.getEmail() == null || emailDto.getEmail().trim().isEmpty()){
            return ResponseEntity.status(400).body("이메일을 입력해주세요.");
        }
        else if(emailDto.getAuthNum() == null){
            return ResponseEntity.status(400).body("인증번호를 입력해주세요.");
        }
        Optional<EmailVerification> emailVerification = mailService.checkMail(emailDto.getEmail());
        if(emailVerification.isPresent() &&
                emailVerification.get().getAuthNum() == emailDto.getAuthNum()){
            return ResponseEntity.ok("인증완료 되었습니다.");
        }
        return ResponseEntity.status(400).body("인증 실패하였습니다.");
    }
}
