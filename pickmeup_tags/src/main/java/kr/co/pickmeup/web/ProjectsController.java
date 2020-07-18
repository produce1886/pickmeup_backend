package kr.co.pickmeup.web;

import kr.co.pickmeup.service.ProjectsService;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectResponseDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectSaveRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectUpdateRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/pickmeup")
public class ProjectsController {
    private final ProjectsService projectsService;

    // 프로젝트 save
    @PostMapping("/projects")
    public Long save(@RequestBody ProjectSaveRequestDto requestDto) {
        return projectsService.save(requestDto);
    }

    // 하나 불러오기 (detail)
    @GetMapping("/projects/{id}")
    public ProjectResponseDto findById(@PathVariable Long id) {
        return projectsService.findById(id);
    }

    // 전체 불러오기
    @GetMapping("/projects")
    public List<ProjectsListResponseDto> findAllDesc() {
        return projectsService.findAllDesc();
    }

    // update
    @PutMapping("/projects/{id}")
    public Long update(@PathVariable Long id, @RequestBody ProjectUpdateRequestDto requestDto) {
        return projectsService.update(id, requestDto);
    }

    // delete
    @DeleteMapping("/projects/{id}")
    public String delete(@PathVariable Long id) {
        projectsService.delete(id);
        return "success";
    }

}
