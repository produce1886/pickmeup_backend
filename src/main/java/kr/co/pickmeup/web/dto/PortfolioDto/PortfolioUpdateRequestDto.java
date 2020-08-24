package kr.co.pickmeup.web.dto.PortfolioDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortfolioUpdateRequestDto {
    private String title;
    private String content;
    private String category;
    private String huntingField;
    private String region;
    private String portfolioCategory;
    private String[] tags;
    private String image;

    @Builder
    public PortfolioUpdateRequestDto(String title, String content, String category, String huntingField, String region, String portfolioCategory, String[] tags, String image) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.huntingField = huntingField;
        this.region = region;
        this.portfolioCategory = portfolioCategory;
        this.tags = tags;

        if(image == null || image == "")
            this.image = null;
        else
            this.image = image;
    }
}
