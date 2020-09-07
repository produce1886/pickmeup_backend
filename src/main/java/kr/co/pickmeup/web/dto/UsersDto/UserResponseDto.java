package kr.co.pickmeup.web.dto.UsersDto;

import kr.co.pickmeup.domain.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserResponseDto {

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
    public UserResponseDto(User entity) {
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        this.image = entity.getImage();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
        this.area = entity.getArea();
        this.interests = entity.getInterests();
        this.introduce = entity.getIntroduce();
        this.sex_security = entity.getSex_security();
        this.birth_security = entity.getBirth_security();
        this.university_security = entity.getUniversity_security();
        this.major_security = entity.getMajor_security();
        this.area_security = entity.getArea_security();
        this.interests_security = entity.getInterests_security();
        this.introduce_security = entity.getIntroduce_security();
    }


}
