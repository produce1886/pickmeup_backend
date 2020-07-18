package kr.co.pickmeup.domain.projects;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

    @Query("SELECT project FROM Projects project ORDER BY project.id DESC")
    List<Projects> findAllDesc();


}
