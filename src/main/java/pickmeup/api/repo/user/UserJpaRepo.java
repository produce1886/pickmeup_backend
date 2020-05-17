package pickmeup.api.repo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pickmeup.api.entity.user.User;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);
}
