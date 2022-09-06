package jung.study.blog_study.controller;

import jung.study.blog_study.config.auth.PrincipalDetail;
import jung.study.blog_study.config.auth.PrincipalDetailService;
import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal PrincipalDetail principal, Model model) {

        UserDto user = userService.findByUsername(principal.getUsername());

        model.addAttribute("user", user);

        return "/user/myPage";
    }

    @PutMapping("/myPage")
    public ResponseEntity<Integer> modify(@RequestBody UserDto newUser, @AuthenticationPrincipal PrincipalDetail principal) {

        System.out.println("newUser = " + newUser);

        userService.modifyUser(newUser);

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
