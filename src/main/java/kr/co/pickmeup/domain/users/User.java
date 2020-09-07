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

    @Column
    private Boolean sex_security;

    @Column
    private Boolean birth_security;

    @Column
    private Boolean university_security;

    @Column
    private Boolean major_security;

    @Column
    private Boolean area_security;

    @Column
    private Boolean introduce_security;

    @Column
    private Boolean interests_security;

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
    public User(String email, String username, String image,
                String sex, String birth, String university, String major, String area, String interests, String introduce,
                Boolean sex_security, Boolean birth_security, Boolean university_security, Boolean major_security, Boolean area_security,
                Boolean interests_security, Boolean introduce_security) {
        this.email = email;
        this.username = username;
        this.image = image;
        this.sex = sex;
        this.birth = birth;
        this.university = university;
        this.major = major;
        this.area = area;
        this.interests = interests;
        this.introduce = introduce;
        this.sex_security = sex_security;
        this.birth_security = birth_security;
        this.university_security = university_security;
        this.major_security = major_security;
        this.area_security = area_security;
        this.interests_security = interests_security;
        this.introduce_security = introduce_security;
    }

    public void update (String email, String username, String image,
                String sex, String birth, String university, String major, String area, String interests, String introduce,
                Boolean sex_security, Boolean birth_security, Boolean university_security, Boolean major_security, Boolean area_security,
                Boolean interests_security, Boolean introduce_security) {
        this.email = email;
        this.username = username;
        this.image = image;
        this.sex = sex;
        this.birth = birth;
        this.university = university;
        this.major = major;
        this.area = area;
        this.interests = interests;
        this.introduce = introduce;
        this.sex_security = sex_security;
        this.birth_security = birth_security;
        this.university_security = university_security;
        this.major_security = major_security;
        this.area_security = area_security;
        this.interests_security = interests_security;
        this.introduce_security = introduce_security;
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
