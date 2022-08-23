package jung.study.blog_study.service;

import jung.study.blog_study.entity.User;

import java.util.List;

public interface UserService {

    User getUser(int id);

    List<User> getAllUsers();

}
