package kr.co.pickmeup.domain.portfolio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    /*
    @Query("SELECT project FROM Project project ORDER BY project.id DESC")
    List<Project> findAllDesc();
     */

    /*
    @Query(value = "select count(*) from portfolio", nativeQuery = true)
    Integer getAllProjectsCount();

    @Query(value = "select sum(view_num) from project", nativeQuery = true)
    Integer getAllViewnumCount();

    @Query(value = "select sum(view_num) from project where id=:project_id", nativeQuery = true)
    Integer getViewnumCount(@Param("project_id") Long project_id);

     */
    List<Portfolio> findByTitleContains(String keyword);

    List<Portfolio> findByContentContains(String keyword);

    Page<Portfolio> findAll(Pageable pageable);

    Page<Portfolio> findByCategory(String category, Pageable pageable);

    Page<Portfolio> findByHuntingField(String huntingField, Pageable pageable);

    Page<Portfolio> findByRegion(String region, Pageable pageable);

    Page<Portfolio> findByPortfolioCategory(String portfolioCategory, Pageable pageable);

}
