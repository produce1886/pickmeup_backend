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
    private String[] tags;
    private String image;
    //private String[] images;

    @Builder
    public PortfolioUpdateRequestDto(String title, String content, String category, String huntingField, String[] tags, String image) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.huntingField = huntingField;
        this.tags = tags;
        this.image = image;
    }
}
