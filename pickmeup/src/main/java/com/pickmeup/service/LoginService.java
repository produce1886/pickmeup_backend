package com.pickmeup.service;


import com.pickmeup.domain.users.UsersRepository;
import com.pickmeup.web.dto.userDto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UsersRepository usersRepository;

    @Transactional
    public void userSave(UserSaveRequestDto requestDto) {
        usersRepository.save(requestDto.toEntity());
    }


}
