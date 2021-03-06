package kr.co.pickmeup.domain.projects;

import kr.co.pickmeup.domain.BaseTimeEntity;
import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
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
public class Project extends BaseTimeEntity {
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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @Column(columnDefinition = "integer default 0")
    private Integer commentsNum = 0;

    @Column(length = 50, nullable = false)
    private String category;

    @Column(length = 50, nullable = false)
    private String huntingField;

    @Column(length = 50, nullable = false)
    private String region;

    @Column(length = 50, nullable = false)
    private String projectCategory;

    @Column(columnDefinition = "TEXT")
    private String image;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectTag> projectTag = new HashSet<>();

    @Builder
    public Project(String title, String content, String email, String category, String huntingField, String region, String projectCategory, String image) {
        this.title = title;
        this.content = content;
        this.email = email;
        this.category = category;
        this.huntingField = huntingField;
        this.region = region;
        this.projectCategory = projectCategory;
        this.image = image;
    }

    public void update(String title, String content, String category, String huntingField, String region, String projectCategory, String image) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.huntingField = huntingField;
        this.region = region;
        this.projectCategory = projectCategory;
        this.image = image;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setProject(this);
    }

    public void addCommentNum() {
        this.commentsNum++;
    }

    public void subtractCommentNum() {
        this.commentsNum--;
    }

    public void addProjectTag(ProjectTag projectTag) {
        this.getProjectTag().add(projectTag);
        projectTag.setProject(this);
    }

    public void deleteProjectTag() {
        this.getProjectTag().clear();
    }

    public void addViewNum() {
        this.viewNum++;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

}
