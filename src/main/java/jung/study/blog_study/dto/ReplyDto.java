package jung.study.blog_study.dto;

import jung.study.blog_study.entity.Board;
import jung.study.blog_study.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {

    private int id;

    private String content;

    private String username;

    private int boardId;

}
