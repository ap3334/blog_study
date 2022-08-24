package jung.study.blog_study.service;

import jung.study.blog_study.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User getUser(int id);

    List<User> getAllUsers();

    Page<User> getAllUsers(int page);

    int saveUser(User user);

    String deleteUser(int id);
}
