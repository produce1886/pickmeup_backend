package kr.co.pickmeup.web;

import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.service.TagService;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/tags")
public class TagController {

    private final TagService tagService;

    // 전체 불러오기 (pagenation 적용)
    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        Page<Tag> projects = tagService.findAll(pageable);
        return new ResponseEntity<> (projects, HttpStatus.OK);
    }
}
