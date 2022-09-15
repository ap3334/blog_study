package jung.study.blog_study.controller;

import jung.study.blog_study.dto.ReplyDto;
import jung.study.blog_study.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/add")
    public ResponseEntity addReply(@RequestBody ReplyDto replyDto) {

        replyService.saveReply(replyDto);

        return new ResponseEntity(HttpStatus.OK);
    }
}
