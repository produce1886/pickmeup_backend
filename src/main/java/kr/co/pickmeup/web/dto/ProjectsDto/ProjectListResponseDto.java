package kr.co.pickmeup.web.dto.ProjectsDto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;

@Getter
public class ProjectListResponseDto {

    private Integer nrOfElements;
    private List pagelist;


    @Builder
    public ProjectListResponseDto(Integer nrOfElements, List pagelist) {
        this.nrOfElements = nrOfElements;
        this.pagelist = pagelist;
    }

}