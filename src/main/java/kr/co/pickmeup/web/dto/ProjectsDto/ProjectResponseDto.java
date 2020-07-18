package kr.co.pickmeup.web.dto.ProjectsDto;

import kr.co.pickmeup.domain.projects.Projects;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Integer viewNum;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ProjectResponseDto(Projects entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.viewNum = entity.getViewNum();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
