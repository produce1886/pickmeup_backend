package kr.co.pickmeup.web;

import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import kr.co.pickmeup.service.ProjectService;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectResponseDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectSaveRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(value="/projects")
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 save
    @PostMapping
    public Long save(@RequestBody ProjectSaveRequestDto requestDto) {
        return projectService.save(requestDto);
    }

    // 하나 불러오기 (detail)
    @GetMapping("/{id}")
    public ProjectResponseDto findById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    // 전체 불러오기 (pagenation 적용) : request URL ex. - http://localhost:8080/projects?page=0&size=10&sort=id,desc
    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        Page<Project> projects = projectService.findAll(pageable);
        return new ResponseEntity<> (projects, HttpStatus.OK);
    }

    // update
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody ProjectUpdateRequestDto requestDto) {
        return projectService.update(id, requestDto);
    }

    // delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        projectService.delete(id);
        return "success";
    }

    // search
    @GetMapping("/search")
    public List<ProjectResponseDto> search(@RequestParam(value = "keyword") String keyword) {
        return projectService.searchProjects(keyword);
    }

}
