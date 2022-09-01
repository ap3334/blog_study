package jung.study.blog_study.service.board;

import jung.study.blog_study.dto.BoardDto;
import jung.study.blog_study.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    int saveContent(BoardDto boardDto, User user);

    Page<BoardDto> getAll(int page);

    BoardDto getBoardById(int id);

    int modifyWrite(BoardDto boardDto);

    void deleteBoard(int id);
}
