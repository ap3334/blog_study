package jung.study.blog_study.repository.reply;

import jung.study.blog_study.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {


    List<Reply> getByBoardId(int id);
}
