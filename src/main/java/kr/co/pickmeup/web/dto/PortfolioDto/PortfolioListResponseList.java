package kr.co.pickmeup.web.dto.PortfolioDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PortfolioListResponseList {

    private Integer nrOfElements;
    private List pagelist;


    @Builder
    public PortfolioListResponseList(Integer nrOfElements, List pagelist) {
        this.nrOfElements = nrOfElements;
        this.pagelist = pagelist;
    }

}