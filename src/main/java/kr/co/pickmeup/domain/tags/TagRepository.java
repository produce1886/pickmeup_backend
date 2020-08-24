package kr.co.pickmeup.domain.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    Boolean existsByName(String name);

//    @Query(value = "select * from tag order by score desc", nativeQuery = true)
//    List<Tag> getTagListByScore();

}
