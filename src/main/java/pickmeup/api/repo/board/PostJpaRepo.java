package pickmeup.api.repo.board;

import org.springframework.data.jpa.repository.JpaRepository;
import pickmeup.api.entity.board.Board;
import pickmeup.api.entity.board.Post;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);
}
