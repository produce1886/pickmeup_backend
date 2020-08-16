package kr.co.pickmeup.domain.projectsTags;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne
    private Project project;

    @Builder
    public ProjectTag(String tag) {
        this.tag = tag;
    }

}
