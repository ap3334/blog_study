package jung.study.blog_study.controller;

import jung.study.blog_study.config.auth.PrincipalDetail;
import jung.study.blog_study.dto.BoardDto;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/auth/list")
    public String index(Model model) {

        List<BoardDto> boards = boardService.getAll();

        System.out.println(boards);

        model.addAttribute("boards", boards);

        return "/index";
    }

    @GetMapping("/write")
    public String writePage() {

        return "/board/write";
    }

    @PostMapping("/write")
    public ResponseEntity<Integer> writeSave(@RequestBody BoardDto boardDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        System.out.println(boardDto);

        User user = principalDetail.getUser();

        int id = boardService.saveContent(boardDto, user);

        System.out.println(id);

        return new ResponseEntity<>(200, HttpStatus.OK);
    }



}
