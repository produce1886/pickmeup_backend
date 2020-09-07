package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.users.User;
import kr.co.pickmeup.domain.users.UserRepository;
import kr.co.pickmeup.web.dto.UsersDto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository personRepository;

    public void login(LoginRequestDto requestDto) {

        if(!personRepository.existsByEmailAndUsername(requestDto.getEmail(), requestDto.getUsername())) {
            // 집어넣기
            User newUser = User.builder()
                    .email(requestDto.getEmail())
                    .username(requestDto.getUsername())
                    .image(requestDto.getImage())
                    .sex(null)
                    .birth(null)
                    .university(null)
                    .major(null)
                    .area(null)
                    .introduce(null)
                    .interests(null)
                    .sex_security(null)
                    .birth_security(null)
                    .university_security(null)
                    .major_security(null)
                    .area_security(null)
                    .introduce_security(null)
                    .interests_security(null)
                    .build();
            personRepository.save(newUser);
        }
    }
}


