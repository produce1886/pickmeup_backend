package kr.co.pickmeup.web.dto.TagsDto;

import lombok.Getter;

@Getter
public class TagPaginationRequestDto {

    private Integer page;
    private Integer size;

    public TagPaginationRequestDto(Integer page, Integer size) {
        this.page = 0;
        this.size = size;
    }

}
