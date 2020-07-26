package kr.co.pickmeup.web.dto.ProjectsDto;

import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectSaveRequestDto {

    private String title;
    private String content;
    private String author;
    private Integer viewNum = 0;

    @Builder
    public ProjectSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Project toEntity() {
        return Project.builder()
                .title(title)
                .content(content)
                .author(author)
                .viewNum(viewNum)
                .build();
    }
}
