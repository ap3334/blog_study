package jung.study.blog_study.service.reply;

import jung.study.blog_study.dto.ReplyDto;

import java.util.List;

public interface ReplyService {
    List<ReplyDto> getRepliesByBoardId(int id);

    void saveReply(ReplyDto replyDto);
}
