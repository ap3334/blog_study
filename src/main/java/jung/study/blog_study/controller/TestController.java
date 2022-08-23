package jung.study.blog_study.controller;

import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.UserRepository;
import jung.study.blog_study.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") int id) {

        User user = userService.getUser(id);

        return user;

    }

    @GetMapping("/user")
    public List<User> getAllUsers() {

        List<User> allUsers = userService.getAllUsers();

        return allUsers;
    }


}
