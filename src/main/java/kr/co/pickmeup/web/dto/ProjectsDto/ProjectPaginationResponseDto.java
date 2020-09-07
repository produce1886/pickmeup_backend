package kr.co.pickmeup.web.dto.ProjectsDto;

import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.tags.Tag;
import lombok.Getter;

import java.util.Set;

@Getter
public class ProjectPaginationResponseDto {
    private Project project;
    private Set<String> tags;

    public ProjectPaginationResponseDto(Project project, Set<String> tags) {
        this.project = project;
        this.tags = tags;
    }
}
