package kr.co.pickmeup.domain.portfoliosTags;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PortfolioTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tag;

    @Setter
    @JsonIgnore
    @ManyToOne
    private Portfolio portfolio;

    @Builder
    public PortfolioTag(String tag) {
        this.tag = tag;
    }

}
