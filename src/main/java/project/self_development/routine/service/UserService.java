package project.self_development.routine.service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import project.self_development.routine.domain.User;
import project.self_development.routine.dto.UserLoginRequestDto;
import project.self_development.routine.dto.UserResponseDto;
import project.self_development.routine.dto.UserSignupRequestDto;
import project.self_development.routine.repository.UserRepository;
import project.self_development.routine.security.CustomUserDetails;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserResponseDto signup(UserSignupRequestDto dto){
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("가입된 이메일입니다.");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return new UserResponseDto(user);
    }
    public UserResponseDto login(UserLoginRequestDto dto, HttpSession session){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        //세션에 저장
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return new UserResponseDto(userDetails.getUser());
    }

}
