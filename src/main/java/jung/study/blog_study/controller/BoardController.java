package jung.study.blog_study.controller;

import jung.study.blog_study.config.auth.PrincipalDetail;
import jung.study.blog_study.dto.BoardDto;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/auth/list")
    public String index(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<BoardDto> boards = boardService.getAll(page);

        System.out.println(boards);

        model.addAttribute("boards", boards);
        model.addAttribute("page", page);

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

    @GetMapping("/detail")
    public String showBoard(int id, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        BoardDto boardDto = boardService.getBoardById(id);

        model.addAttribute("board", boardDto);
        model.addAttribute("member", principalDetail);

        return "/board/detail";

    }

    @GetMapping("/modify")
    public String modifyPage(int id, Model model) {

        BoardDto boardDto = boardService.getBoardById(id);

        model.addAttribute("board", boardDto);

        return "/board/modify";
    }

    @PutMapping("/modify")
    public ResponseEntity<Integer> modifySave(@RequestBody BoardDto boardDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        int id = boardService.modifyWrite(boardDto);

        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Integer> removeBoard(@RequestBody int id) {

        boardService.deleteBoard(id);

        return new ResponseEntity<>(200, HttpStatus.OK);
    }


}
