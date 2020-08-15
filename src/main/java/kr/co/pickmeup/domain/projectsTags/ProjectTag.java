package kr.co.pickmeup.domain.projectsTags;

import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ProjectTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tag;

    @Setter
    @ManyToOne
    private Project project;

    @Builder
    public ProjectTag(String tag) {
        this.tag = tag;
    }

}
