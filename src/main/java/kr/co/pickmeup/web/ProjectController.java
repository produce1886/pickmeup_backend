package kr.co.pickmeup.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = {"1. 프로젝트"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/projects")
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 save
    @ApiOperation(value="프로젝트 생성", notes="프로젝트를 생성한다.")
    @PostMapping
    public Long save(@RequestBody ProjectSaveRequestDto requestDto) {
        return projectService.save(requestDto);
    }

    // 하나 불러오기 (detail)
    @ApiOperation(value="프로젝트 상세조회", notes="해당 id의 프로젝트를 상세 조회한다.")
    @GetMapping("/{id}")
    public ProjectResponseDto findById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    // 전체 불러오기 (pagenation 적용)
    @ApiOperation(value="전체 프로젝트 조회", notes="모든 프로젝트를 조회한다. 페이지네이션으로 조절가능.")
    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        Page<Project> projects = projectService.findAll(pageable);
        return new ResponseEntity<> (projects, HttpStatus.OK);
    }

    // update
    @ApiOperation(value="프로젝트 수정", notes="해당 id의 프로젝트를 수정한다.")
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody ProjectUpdateRequestDto requestDto) {
        return projectService.update(id, requestDto);
    }

    // delete
    @ApiOperation(value="프로젝트 삭제", notes="해당 id의 프로젝트를 삭제한다.")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        projectService.delete(id);
        return "success";
    }



}
