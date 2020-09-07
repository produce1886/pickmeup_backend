package kr.co.pickmeup.web.dto.UsersDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserUpdateDto {

    private String email;
    private String username;
    private String sex;
    private String birth;
    private String university;
    private String major;
    private String area;
    private String introduce;
    private String image;
    private String interests;
    private Boolean sex_security;
    private Boolean birth_security;
    private Boolean university_security;
    private Boolean major_security;
    private Boolean area_security;
    private Boolean introduce_security;
    private Boolean interests_security;

    @Builder
    public UserUpdateDto(String email, String username, String sex, String birth, String university, String major,
                           String area, String introduce, String image, String interests, Boolean sex_security, Boolean birth_security,
                           Boolean university_security, Boolean major_security, Boolean area_security, Boolean introduce_security,
                           Boolean interests_security) {
        this.email = email;
        this.username = username;
        this.sex = sex;
        this.birth = birth;
        this.university = university;
        this.major = major;
        this.area = area;
        this.image = image;
        this.introduce = introduce;
        this.interests = interests;
        this.sex_security = sex_security;
        this.birth_security = birth_security;
        this.university_security = university_security;
        this.major_security = major_security;
        this.area_security = area_security;
        this.introduce_security = introduce_security;
        this.interests_security = interests_security;
    }

}
