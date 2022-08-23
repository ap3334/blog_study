package jung.study.blog_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogStudyApplication.class, args);
    }

}
