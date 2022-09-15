package jung.study.blog_study.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jung.study.blog_study.config.auth.PrincipalDetail;
import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.entity.KakaoProfile;
import jung.study.blog_study.entity.OAuthToken;
import jung.study.blog_study.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Value("${jung.key}")
    private String adminKey;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @GetMapping("/auth/login")
    public String loginPage() {

        return "/user/login";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {

        UserDto userDto = userService.getUserDto(code);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), adminKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @GetMapping("/auth/join")
    public String joinPage() {

        return "/user/join";
    }

    @PostMapping("/auth/join")
    public ResponseEntity<Integer> join(@RequestBody UserDto user) {

        UserDto dto = userService.saveUser(user);

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
