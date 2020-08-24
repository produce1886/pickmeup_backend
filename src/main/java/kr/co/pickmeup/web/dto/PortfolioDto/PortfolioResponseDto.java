package kr.co.pickmeup.web.dto.PortfolioDto;

import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.users.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class PortfolioResponseDto {
    private Long id;
    private String title;
    private String content;
    private String email;
    private Integer viewNum;
    private String category;
    private String huntingField;
    private String region;
    private String portfolioCategory;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;
    private Set<ProjectTag> projectTag;
    private String image;

    public PortfolioResponseDto(Portfolio entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getEmail();
        this.viewNum = entity.getViewNum();
        this.category = entity.getCategory();
        this.huntingField = entity.getHuntingField();
        this.region = entity.getRegion();
        this.portfolioCategory = entity.getPortfolioCategory();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.user = entity.getUser();
        this.projectTag = entity.getProjectTag();
        this.image = entity.getImage();
    }
}