package kr.co.pickmeup.web.dto.PortfolioDto;

import kr.co.pickmeup.domain.portfolio.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
public class PortfolioSaveRequestDto {

    private String title;
    private String content;
    private String email;
    private String category;
    private String huntingField;
    private String[] tags;
    private String image;
   // private String[] images;

    @Builder
    public PortfolioSaveRequestDto(String title, String content, String email, String category, String huntingField, String region, String portfolioCategory, String[] tags, String image) {
        this.title = title;
        this.content = content;
        this.email = email;
        this.category = category;
        this.huntingField = huntingField;
        this.tags = tags;
        this.image = image;
    }

    public Portfolio toEntity() {
        return Portfolio.builder()
                .title(title)
                .content(content)
                .email(email)
                .category(category)
                .huntingField(huntingField)
                .image(image)
                .build();
    }
}
