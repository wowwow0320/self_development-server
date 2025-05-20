package project.self_development.routine.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UserSignupRequestDto {
    private String name;
    private String email;
    private String password;
}
