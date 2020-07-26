package kr.co.pickmeup.web;

import io.swagger.annotations.ApiOperation;
import kr.co.pickmeup.domain.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    @GetMapping
    public String test() {
        return "test";
    }
}
