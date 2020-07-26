package kr.co.pickmeup.domain.projects;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    /*
    @Query("SELECT project FROM Project project ORDER BY project.id DESC")
    List<Project> findAllDesc();

     */


    Page<Project> findAll(Pageable pageable);

}
