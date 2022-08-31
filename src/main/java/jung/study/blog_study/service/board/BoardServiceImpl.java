package jung.study.blog_study.service.board;

import jung.study.blog_study.dto.BoardDto;
import jung.study.blog_study.entity.Board;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.board.BoardRepository;
import jung.study.blog_study.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;

  private final UserRepository userRepository;


    @Transactional
    @Override
    public int saveContent(BoardDto boardDto, User user) {

        boardDto.setUsername(user.getUsername());

        Board board = dtoToEntity(boardDto);

        boardRepository.save(board);

        return board.getId();
    }

    private Board dtoToEntity(BoardDto dto) {

        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> {
            return new UsernameNotFoundException("해당하는 사용자를 찾을 수 없습니다.");
        });

        Board entity = Board.builder()
            .id(dto.getId())
            .title(dto.getTitle())
            .content(dto.getContent())
            .count(dto.getCount())
            .user(user)
            .build();

        return entity;
    }

}
