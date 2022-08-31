package jung.study.blog_study.service.board;

import jung.study.blog_study.dto.BoardDto;
import jung.study.blog_study.entity.User;

public interface BoardService {

    int saveContent(BoardDto boardDto, User user);

}
