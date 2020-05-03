package com.pickmeup.web.controller;

import com.pickmeup.advice.CIdSigninFailedException;
import com.pickmeup.config.security.JwtTokenProvider;
import com.pickmeup.domain.users.Users;
import com.pickmeup.domain.users.UsersRepository;
import com.pickmeup.response.CommonResult;
import com.pickmeup.response.SingleResult;
import com.pickmeup.service.LoginService;
import com.pickmeup.service.ResponseService;
import com.pickmeup.web.dto.userDto.LoginResponseDto;
import com.pickmeup.web.dto.userDto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api")
public class UserController {


    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;
    private final UsersRepository usersRepository;

    // 계정 생성
    @PostMapping(value = "/signup")
    public CommonResult signUp (@RequestBody UserSaveRequestDto requestDto) {

        loginService.userSave(requestDto);

        return responseService.getSuccessResult();

    }

    // 로그인
    @PostMapping(value="/signin")
    public SingleResult<LoginResponseDto> signIn(@RequestBody UserSaveRequestDto requestDto) {

        Users user = usersRepository.findByEmail(requestDto.getEmail()).orElseThrow(CIdSigninFailedException::new);
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()) )
            throw new CIdSigninFailedException();

        // 토큰이랑 권한을 data dto에 담아줌.
        String token = jwtTokenProvider.createToken(String.valueOf(user.getEmail()), user.getRole());
        String authority = user.getRole().get(0);
        LoginResponseDto responseDto = new LoginResponseDto(token, authority);

        return responseService.getSingleResult(responseDto);

    }

}
