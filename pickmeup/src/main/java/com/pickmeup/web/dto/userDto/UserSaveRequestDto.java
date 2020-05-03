package com.pickmeup.web.dto.userDto;

import com.pickmeup.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String name;
    private String sex;
    private String birth;
    private String university;
    private String major;
    private String area;
    private String like;
    private String introduce;
    private List<String> role;

    @Builder
    public UserSaveRequestDto(String email, String password, String name, String sex, String birth,
                              String university, String major, String area, String like, String introduce) {
        this.email = email;
        this.password = "{noop}" + password;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.university = university;
        this.major = major;
        this.area = area;
        this.like = like;
        this.introduce = introduce;
        this.role = Collections.singletonList("ADMIN");
    }

    public Users toEntity() {
        return Users.builder()
                .email(email)
                .password("{noop}" + password)
                .name(name)
                .sex(sex)
                .birth(birth)
                .university(university)
                .major(major)
                .area(area)
                .like(like)
                .introduce(introduce)
                .role(Collections.singletonList("USER"))
                .build();

    }
}
