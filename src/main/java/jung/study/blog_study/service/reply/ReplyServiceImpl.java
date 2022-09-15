package jung.study.blog_study.service.reply;

import jung.study.blog_study.dto.ReplyDto;
import jung.study.blog_study.entity.Board;
import jung.study.blog_study.entity.Reply;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.board.BoardRepository;
import jung.study.blog_study.repository.reply.ReplyRepository;
import jung.study.blog_study.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    @Override
    public List<ReplyDto> getRepliesByBoardId(int id) {

        List<Reply> replyList = replyRepository.getByBoardId(id);

        return replyList.stream().map(i -> entityToDto(i)).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public void saveReply(ReplyDto replyDto) {

        replyRepository.save(dtoToEntity(replyDto));

    }

    private Reply dtoToEntity(ReplyDto replyDto) {

        User user = userRepository.findByUsername(replyDto.getUsername()).get();

        Board board = boardRepository.findById(replyDto.getBoardId()).get();

        Reply reply = Reply.builder()
                .id(replyDto.getId())
                .content(replyDto.getContent())
                .user(user)
                .board(board)
                .build();

        return reply;
    }

    private ReplyDto entityToDto(Reply reply) {

        ReplyDto dto = ReplyDto.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .username(reply.getUser().getUsername())
                .boardId(reply.getBoard().getId())
                .build();

        return dto;

    }

}
