package kr.co.pickmeup.web;

import kr.co.pickmeup.service.UserService;

import kr.co.pickmeup.web.dto.UsersDto.UserResponseDto;
import kr.co.pickmeup.web.dto.UsersDto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/users")
public class UserController {

    private final UserService userService;

    // detail
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        UserResponseDto result = userService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // update
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserUpdateDto requestDto) {
        return userService.update(id, requestDto);
    }

}
