package kr.co.pickmeup.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=40, nullable = false)
    private String email;

    @Column(length=40, nullable = false)
    private String username;


    @Column(length=10)
    private String sex;

    @Column(length=10)
    private String birth;


    @Column(length=20)
    private String university;

    @Column(length=20)
    private String major;


    @Column(length=20)
    private String area;

    @Column(length=50)
    private String introduce;

    @Column(length=200)
    private String image;

    @Column(length=30)
    private String interests;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Project> projects = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Portfolio> portfolios = new HashSet<>();

    @Builder
    public User(String email, String username, String sex, String birth,
                String university, String major, String area, String interests, String introduce, String image) {
        this.email = email;
        this.username = username;
        this.sex = sex;
        this.birth = birth;
        this.university = university;
        this.major = major;
        this.area = area;
        this.interests = interests;
        this.introduce = introduce;
        this.image = image;
    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.setUser(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public void addPortfolio(Portfolio portfolio) {
        this.portfolios.add(portfolio);
        portfolio.setUser(this);
    }
}
