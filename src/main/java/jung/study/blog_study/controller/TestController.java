package jung.study.blog_study.controller;

import jung.study.blog_study.entity.User;
import jung.study.blog_study.repository.UserRepository;
import jung.study.blog_study.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/page")
    public Page<User> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page) {

        Page<User> allUsers = userService.getAllUsers(page);

        return allUsers;

    }


}
