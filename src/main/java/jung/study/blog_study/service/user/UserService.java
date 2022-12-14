package jung.study.blog_study.service.user;

import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User getUser(int id);

    List<User> getAllUsers();

    Page<User> getAllUsers(int page);

    UserDto saveUser(UserDto userDto);

    String deleteUser(int id);

    UserDto findByUsername(String username);

    void modifyUser(UserDto userDto);

    UserDto getUserDto(String code);

    // 시큐리티 없이 로그인
    // UserDto loginUser(String username, String password);

    default User dtoToEntity(UserDto dto) {

        User entity = User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .oauth(dto.getOauth())
                .build();

        return entity;

    }

    default UserDto entityToDto(User entity) {

        UserDto dto = UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .oauth(entity.getOauth())
                .build();

        return dto;

    }

}
