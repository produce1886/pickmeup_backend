package kr.co.pickmeup.web.dto.PortfolioDto;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.portfoliosTags.PortfolioTag;
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
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;
    private String image;
    private Set<PortfolioTag> portfolioTag;
    private Set<Comment> comments;
    private Integer commentsNum;



    public PortfolioResponseDto(Portfolio entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getEmail();
        this.viewNum = entity.getViewNum();
        this.category = entity.getCategory();
        this.huntingField = entity.getHuntingField();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.user = entity.getUser();
        this.image = entity.getImage();
        this.portfolioTag = entity.getPortfolioTag();
        this.comments = entity.getComments();
        this.commentsNum = entity.getCommentsNum();

    }
}