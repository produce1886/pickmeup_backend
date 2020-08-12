package kr.co.pickmeup.web.dto.ProjectsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequestDto {
    private String title;
    private String content;
    private String category;
    private String huntingField;
    private String region;
    private String projectCategory;

    @Builder
    public ProjectUpdateRequestDto(String title, String content, String category, String huntingField, String region, String projectCategory) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.huntingField = huntingField;
        this.region = region;
        this.projectCategory = projectCategory;
    }
}
