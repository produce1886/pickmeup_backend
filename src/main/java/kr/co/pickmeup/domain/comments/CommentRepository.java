package kr.co.pickmeup.domain.comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAll(Pageable pageable);

    @Query(value = "select count(*) from comment where project_id=:project_id", nativeQuery = true)
    Integer getCommentsCount(@Param("project_id") Long project_id);

    @Query(value = "select count(*) from comment", nativeQuery = true)
    Integer getAllCommentsCount();

//    List<Comment> findAllByProjectId(Integer project_id);
}
