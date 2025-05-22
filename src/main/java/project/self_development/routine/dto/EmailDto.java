package project.self_development.routine.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class EmailDto {
    private String email;
    private Integer authNum;
}
