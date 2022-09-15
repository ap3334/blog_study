package jung.study.blog_study.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.entity.KakaoProfile;
import jung.study.blog_study.entity.OAuthToken;
import jung.study.blog_study.entity.Role;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${jung.key}")
    private String adminKey;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Override
    public User getUser(int id) {

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 존재하지 않습니다.");
        });

        return user;

    }

    @Override
    public List<User> getAllUsers() {

        List<User> allUsers = userRepository.findAll();

        return allUsers;
    }

    @Override
    public Page<User> getAllUsers(int page) {

        Pageable pageable = PageRequest.of(page, 10);

        Page<User> allUsers = userRepository.findAll(pageable);

        return allUsers;

    }

    @Transactional
    @Override
    public UserDto saveUser(UserDto userDto) {

        String encPassword = encoder.encode(userDto.getPassword());
        userDto.setPassword(encPassword);

        userDto.setRole(Role.USER);

        User user = dtoToEntity(userDto);

        User save = userRepository.save(user);

        return entityToDto(save);

    }

    @Override
    public String deleteUser(int id) {

        try {
            userRepository.deleteById(id);
        }  catch (EmptyResultDataAccessException e) {
            return "해당 유저는 존재하지 않습니다.";
        }

        return "해당 유저는 삭제되었습니다. id =  " + id;

    }

    @Override
    public UserDto findByUsername(String username) {

        User user = userRepository.findByUsername(username).orElse(new User());

        return entityToDto(user);
    }

    @Transactional
    @Override
    public void modifyUser(UserDto userDto) {

        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        if (user.getOauth() == null) {

            if (!userDto.getPassword().equals("")) {
                String encPassword = encoder.encode(userDto.getPassword());
                user.changePassword(encPassword);
                System.out.println("change pw");
            }

        }

        user.changeEmail(userDto.getEmail());
        System.out.println("change email");

    }

    public UserDto getUserDto(String code) {
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

        UserDto byUsername = findByUsername(userDto.getUsername());

        System.out.println(byUsername);

        if (byUsername.getUsername() == null) {
            saveUser(userDto);
        }

        return userDto;
    }


/*
    // 시큐리티 없이 로그인
    @Transactional(readOnly = true)
    @Override
    public UserDto loginUser(String username, String password) {

        User user = userRepository.findUserByUsernameAndPassword(username, password);

        return entityToDto(user);

    }
*/


}
