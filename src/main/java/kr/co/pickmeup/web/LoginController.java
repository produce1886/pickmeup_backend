package kr.co.pickmeup.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.service.LoginService;
import kr.co.pickmeup.web.dto.UsersDto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. 로그인"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/login")
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value="로그인", notes="유저가 로그인을 한다.")
    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequestDto requestDto) {
        loginService.login(requestDto);
        return new ResponseEntity<> (HttpStatus.OK);
    }

}
