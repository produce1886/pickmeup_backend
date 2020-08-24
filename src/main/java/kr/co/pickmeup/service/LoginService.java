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
                    .sex(null)
                    .birth(null)
                    .university(null)
                    .major(null)
                    .area(null)
                    .introduce(null)
                    .interests(null)
                    .image(requestDto.getImage())
                    .build();
            personRepository.save(newUser);
        }
    }
}


