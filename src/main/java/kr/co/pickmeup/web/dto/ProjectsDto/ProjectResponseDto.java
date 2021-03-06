package kr.co.pickmeup.web.dto.ProjectsDto;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.users.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class ProjectResponseDto {
    private Long id;
    private String title;
    private String content;
    private String email;
    private Integer viewNum;
    private Set<Comment> comments;
    private Integer commentsNum;
    private String category;
    private String huntingField;
    private String region;
    private String projectCategory;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;
    private Set<ProjectTag> projectTag;
    private String image;

    public ProjectResponseDto(Project entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getEmail();
        this.viewNum = entity.getViewNum();
        this.comments = entity.getComments();
        this.commentsNum = entity.getCommentsNum();
        this.category = entity.getCategory();
        this.huntingField = entity.getHuntingField();
        this.region = entity.getRegion();
        this.projectCategory = entity.getProjectCategory();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.user = entity.getUser();
        this.projectTag = entity.getProjectTag();
        this.image = entity.getImage();
    }
}