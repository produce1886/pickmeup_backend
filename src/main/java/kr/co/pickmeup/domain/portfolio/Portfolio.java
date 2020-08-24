package kr.co.pickmeup.domain.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.pickmeup.domain.BaseTimeEntity;
import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.portfoliosTags.PortfolioTag;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Portfolio extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String email;

    @Column(columnDefinition = "integer default 0")
    private Integer viewNum = 0;

    @Setter
    @ManyToOne
    private User user;

    @Column(length = 50, nullable = false)
    private String category;

    @Column(length = 50, nullable = false)
    private String huntingField;

    @Column(length = 300)
    private String image; // 여러개로 수정해야함

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private Set<PortfolioTag> portfolioTag = new HashSet<>();

    @Builder
    public Portfolio(String title, String content, String email, String category, String huntingField, String image) {
        this.title = title;
        this.content = content;
        this.email = email;
        this.category = category;
        this.huntingField = huntingField;
        this.image = image;
    }

    public void update(String title, String content, String category, String huntingField, String image) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.huntingField = huntingField;
        this.image = image; // 일단은 하나
    }


    public void addPortfolioTag(PortfolioTag portfolioTag) {
        this.getPortfolioTag().add(portfolioTag);
        portfolioTag.setPortfolio(this);
    }

    public void deletePortfolioTag() {
        this.getPortfolioTag().clear();
    }

    public void addViewNum() {
        this.viewNum++;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

}
