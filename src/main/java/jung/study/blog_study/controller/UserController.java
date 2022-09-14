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

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "261926a3e415529fb89a88f7031b67eb");
        params.add("redirect_uri", "http://localhost:8080/user/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        UserDto userDto = UserDto.builder()
                .username(kakaoProfile.getProperties().getNickname() + kakaoProfile.getId())
                .password(adminKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        System.out.println(userDto);

        UserDto byUsername = userService.findByUsername(userDto.getUsername());

        System.out.println(byUsername);

        if (byUsername.getUsername() == null) {
            userService.saveUser(userDto);
        }

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
