package kr.co.pickmeup.web.dto.ProjectsDto;

import lombok.Getter;

@Getter
public class ProjectPaginationRequestDto {
//    private boolean totalList;
    private Integer page;
    private Integer size;
    private String sortColumn;


    public ProjectPaginationRequestDto(Integer page, Integer size, String sortColumn) {
//        this.totalList = totalList;
        this.page = page;
        this.size = size;
        this.sortColumn = sortColumn;
    }
}
