package kr.co.pickmeup.web.dto.PortfolioDto;

import lombok.Getter;

@Getter
public class PortfolioPaginationRequestDto {

    private Integer page;
    private Integer size;
    private String sortColumn;
    private String category;
    private String huntingField;
    private String keyword;

    public PortfolioPaginationRequestDto(Integer page, Integer size, String sortColumn, String category, String huntingField, String keyword) {
        this.page = page;
        this.size = size;
        this.sortColumn = sortColumn;
        this.category = category;
        this.huntingField = huntingField;
        this.keyword = keyword;
    }

}
