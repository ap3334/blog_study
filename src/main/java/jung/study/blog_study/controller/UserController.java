package jung.study.blog_study.controller;

import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.service.user.UserService;
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

    @GetMapping("/auth/login")
    public String loginPage() {

        return "/user/login";
    }


    @GetMapping("/auth/join")
    public String joinPage() {

        return "/user/join";
    }

    @PostMapping("/auth/join")
    public ResponseEntity<Integer> join(@RequestBody UserDto user) {

        int id = userService.saveUser(user);

        return new ResponseEntity<>(200, HttpStatus.OK);
    }




/*
    // 시큐리티 없이 로그인
    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody UserDto user) {

        UserDto principal = userService.loginUser(user.getUsername(), user.getPassword());

        if(principal != null) {
            session.setAttribute("principal", principal);
            return new ResponseEntity<>(200, HttpStatus.OK);
        }

        return new ResponseEntity<>(500, HttpStatus.OK);


    }
*/

}
