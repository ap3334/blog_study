package jung.study.blog_study.entity;

import jung.study.blog_study.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class EntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void InsertDummies() {

        IntStream.rangeClosed(1, 20).forEach(i -> {

            User user = User.builder()
                    .username("user" + i)
                    .password("1234")
                    .email("user" + i + "@jung.com")
                    .role(Role.USER)
                    .build();

            userRepository.save(user);

        });

    }
}
