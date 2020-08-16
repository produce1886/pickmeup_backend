package kr.co.pickmeup.web.dto.UsersDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    private String email;
    private String username;
    private String image;

    @Builder
    public LoginRequestDto(String email, String username, String image) {
        this.email = email;
        this.username = username;
        this.image = image;
    }
}
