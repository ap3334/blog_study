package jung.study.blog_study.controller;

import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {

        return "/user/login";
    }


    @GetMapping("/join")
    public String join() {

        return "/user/join";
    }

    @PostMapping("/join")
    public ResponseEntity<Integer> saveUser(@RequestBody UserDto user) {

        System.out.println(user);

        int id = userService.saveUser(user);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
