package jung.study.blog_study.service.board;

import jung.study.blog_study.dto.BoardDto;
import jung.study.blog_study.entity.Board;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.board.BoardRepository;
import jung.study.blog_study.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Page<BoardDto> getAll(int page) {

        Pageable pageable = PageRequest.of(page, 4, Sort.by("regDate").descending());

        Page<BoardDto> boardDtos = boardRepository.findAll(pageable).map(board -> entityToDto(board));

        return boardDtos;
    }

    @Override
    public BoardDto getBoardById(int id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 존재하지 않습니다."));

        return entityToDto(board);
    }

    @Override
    public int modifyWrite(BoardDto boardDto) {

        Board board = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 존재하지 않습니다."));

        board.changeTitle(boardDto.getTitle());
        board.changeContent(boardDto.getContent());

        boardRepository.save(board);

        return board.getId();

    }

    @Override
    public void deleteBoard(int id) {

        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("해당하는 글을 삭제할 수 없습니다.");
        }
    }

    private BoardDto entityToDto(Board board) {

        BoardDto dto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getUser().getUsername())
                .count(board.getCount())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        return dto;

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
