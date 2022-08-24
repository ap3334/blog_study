package jung.study.blog_study.dto;

import jung.study.blog_study.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    private String username;

    private String password;

    private String email;

    private Role role;

    private LocalDateTime regDate, modDate;

}
