package jung.study.blog_study.service;

import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User getUser(int id);

    List<User> getAllUsers();

    Page<User> getAllUsers(int page);

    int saveUser(UserDto userDto);

    String deleteUser(int id);

    default User dtoToEntity(UserDto dto) {

        User entity = User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();

        return entity;

    }

}
