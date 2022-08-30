package jung.study.blog_study.repository;

import jung.study.blog_study.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // 시큐리티 없이 로그인
//    User findUserByUsernameAndPassword(String username, String password);

}
