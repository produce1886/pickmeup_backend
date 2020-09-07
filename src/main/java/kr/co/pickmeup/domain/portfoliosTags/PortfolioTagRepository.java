package kr.co.pickmeup.domain.portfoliosTags;

import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PortfolioTagRepository extends JpaRepository<PortfolioTag, Long> {

    @Query(value = "select portfolio_id from portfolio_tag where tag=:tag_name", nativeQuery = true)
    List<Long> getPortfolioIdListByHashtag(@Param("tag_name") String tag_name);

    @Query(value = "select tag from portfolio_tag where portfolio_id=:portfolio_id", nativeQuery = true)
    Set<String> getTagListByPortfolioId(@Param("portfolio_id") Long portfolio_id);

    List<PortfolioTag> findAllByPortfolio(Portfolio portfolio);

}
