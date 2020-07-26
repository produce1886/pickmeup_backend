package kr.co.pickmeup.domain.users;

import kr.co.pickmeup.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 20)
    private String name;

    private String sex;

    private String university;

    private String major;

    private String area;

    private String like;

    private String introduce;

    private String image;

    @Builder
    public User(String email, String name, String sex, String university, String major, String area,
                String like, String introduce, String image) {
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.university = university;
        this.major = major;
        this.area = area;
        this.like = like;
        this.introduce = introduce;
        this.image = image;
    }



}
