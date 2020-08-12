package kr.co.pickmeup.domain.projects;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    /*
    @Query("SELECT project FROM Project project ORDER BY project.id DESC")
    List<Project> findAllDesc();
     */

    @Query(value = "select count(*) from project", nativeQuery = true)
    Integer getAllProjectsCount();

    @Query(value = "select sum(view_num) from project", nativeQuery = true)
    Integer getAllViewnumCount();

    @Query(value = "select sum(view_num) from project where id=:project_id", nativeQuery = true)
    Integer getViewnumCount(@Param("project_id") Long project_id);

    Page<Project> findAll(Pageable pageable);

    List<Project> findByTitleContains(String keyword);

    List<Project> findByContentContains(String keyword);

}
