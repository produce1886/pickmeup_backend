package kr.co.pickmeup.domain.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.pickmeup.domain.BaseTimeEntity;
import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @JsonIgnore
    @Setter
    @ManyToOne
    private Project project;

    @Builder
    public Comment(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public void update(String content) {
        this.content = content;
    }

}
