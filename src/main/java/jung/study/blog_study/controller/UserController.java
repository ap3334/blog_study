package jung.study.blog_study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {

        return "/user/login";
    }


    @GetMapping("/join")
    public String join() {

        return "/user/join";
    }
}