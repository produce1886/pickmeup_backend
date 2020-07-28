package kr.co.pickmeup.domain.projects;

import kr.co.pickmeup.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Project extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private Integer viewNum;

    @Builder
    public Project(String title, String content, String author, Integer viewNum) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewNum = viewNum;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void add_viewNum() {
        this.viewNum++;
    }

}
