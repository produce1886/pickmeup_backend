package kr.co.pickmeup.domain.projectsTags;

import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {

    @Query(value = "select project_id from project_tag where tag=:tag_name", nativeQuery = true)
    List<Long> getProjectIdListByHashtag(@Param("tag_name") String tag_name);

    List<ProjectTag> findAllByProject(Project project);

}
