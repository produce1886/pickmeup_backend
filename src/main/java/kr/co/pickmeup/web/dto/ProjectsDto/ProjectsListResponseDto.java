package kr.co.pickmeup.web.dto.ProjectsDto;

import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectsListResponseDto {
    private Long id;
    private String title;
    private String email;
    private Integer viewNum;
    private LocalDateTime modifiedDate;
    // 태그
    // 댓글카운트

    public ProjectsListResponseDto(Project entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.email = entity.getEmail();
        this.viewNum = entity.getViewNum();
        this.modifiedDate = entity.getModifiedDate();
    }

}
