package pickmeup.api.repo.board;

import org.springframework.data.jpa.repository.JpaRepository;
import pickmeup.api.entity.board.Board;

public interface BoardJpaRepo extends JpaRepository<Board, Long> {
    Board findByName(String name);
}
