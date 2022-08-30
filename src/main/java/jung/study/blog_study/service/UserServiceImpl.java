package jung.study.blog_study.service;

import jung.study.blog_study.dto.UserDto;
import jung.study.blog_study.entity.Role;
import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
    public int saveUser(UserDto userDto) {

        String encPassword = encoder.encode(userDto.getPassword());
        userDto.setPassword(encPassword);

        userDto.setRole(Role.USER);

        User user = dtoToEntity(userDto);

        userRepository.save(user);

        return user.getId();

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
