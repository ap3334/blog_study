package jung.study.blog_study.service;

import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

}
