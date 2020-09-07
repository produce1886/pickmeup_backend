package kr.co.pickmeup.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndUsername(String email, String username);

    Optional<User> findByEmail(String email);

}
