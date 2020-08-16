package kr.co.pickmeup.web.dto.ProjectsDto;

import lombok.Getter;

@Getter
public class ProjectPaginationRequestDto {
    private Integer page;
    private Integer size;
    private String sortColumn;
    private String category;
    private String huntingField;
    private String region;
    private String projectCategory;
    private String keyword;

    public ProjectPaginationRequestDto(Integer page, Integer size, String sortColumn, String category, String huntingField,
                                       String region, String projectCategory, String keyword) {
        this.page = page;
        this.size = size;
        this.sortColumn = sortColumn;
        this.category = category;
        this.huntingField = huntingField;
        this.region = region;
        this.projectCategory = projectCategory;
        this.keyword = keyword;
    }
}
