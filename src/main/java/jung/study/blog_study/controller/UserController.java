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

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final HttpSession session;

    @GetMapping("/login")
    public String loginPage() {

        return "/user/login";
    }


    @GetMapping("/join")
    public String joinPage() {

        return "/user/join";
    }

    @PostMapping("/join")
    public ResponseEntity<Integer> join(@RequestBody UserDto user) {

        System.out.println(user);

        int id = userService.saveUser(user);

        return new ResponseEntity<>(200, HttpStatus.OK);
    }




    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody UserDto user) {

        UserDto principal = userService.loginUser(user.getUsername(), user.getPassword());

        if(principal != null) {
            session.setAttribute("principal", principal);
            return new ResponseEntity<>(200, HttpStatus.OK);
        }

        return new ResponseEntity<>(500, HttpStatus.OK);


    }

}
