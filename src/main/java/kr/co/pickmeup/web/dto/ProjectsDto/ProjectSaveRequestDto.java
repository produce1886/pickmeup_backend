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
    private String category;
    private String huntingField;
    private String region;
    private String projectCategory;

    @Builder
    public ProjectSaveRequestDto(String title, String content, String author, String category, String huntingField, String region, String projectCategory) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.huntingField = huntingField;
        this.region = region;
        this.projectCategory = projectCategory;
    }

    public Project toEntity() {
        return Project.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .huntingField(huntingField)
                .region(region)
                .projectCategory(projectCategory)
                .build();
    }
}
