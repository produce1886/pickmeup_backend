package kr.co.pickmeup.web.dto.ProjectsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public ProjectUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
