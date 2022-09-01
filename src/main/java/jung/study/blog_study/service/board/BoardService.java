package jung.study.blog_study.service.board;

import jung.study.blog_study.dto.BoardDto;
import jung.study.blog_study.entity.User;

import java.util.List;

public interface BoardService {

    int saveContent(BoardDto boardDto, User user);

    List<BoardDto> getAll();
}
