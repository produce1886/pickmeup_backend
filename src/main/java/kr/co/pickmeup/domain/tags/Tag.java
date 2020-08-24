package kr.co.pickmeup.domain.tags;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.pickmeup.domain.BaseTimeEntity;
import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    @Setter
    private double score;

    private Integer count;

//    @JsonIgnore
//    @Setter
//    @ManyToOne
//    private Project project;
    
    @Builder
    public Tag(String name, double score, Integer count) {
        this.name = name;
        this.score = score;
        this.count = count;
    }

    public void add_count() {
        this.count++;
    }

    public void subtract_count() {
        this.count--;
    }

}
