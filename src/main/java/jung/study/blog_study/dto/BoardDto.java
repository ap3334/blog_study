package jung.study.blog_study.dto;

import jung.study.blog_study.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private int id;

    private String title;

    private String content;

    private int count;

    private String username;

    private LocalDateTime regDate, modDate;

}
