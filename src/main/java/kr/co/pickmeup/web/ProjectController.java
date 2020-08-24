package kr.co.pickmeup.web;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.service.CommentService;
import kr.co.pickmeup.service.ProjectService;
import kr.co.pickmeup.web.dto.CommentsDto.CommentCountResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentSaveRequestDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentUpdateRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final CommentService commentService;

    // 프로젝트 save
    @PostMapping()
    public Long save(@RequestBody ProjectSaveRequestDto requestDto) {
        return projectService.save(requestDto);
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id) {

        ProjectResponseDto result = projectService.findById(id);
        String title = result.getTitle();
        System.out.println(title);
        return title;
    }

    @PostMapping("list")
    public ResponseEntity findAll(Pageable pageable, @RequestBody ProjectPaginationRequestDto requestDto) {
        PagedListHolder page = projectService.findAll(requestDto);
        return new ResponseEntity<>(page.getPageList(), HttpStatus.OK);
    }

    @GetMapping("categoryFilter")
    public ResponseEntity findByCategory(@RequestParam String category, Pageable pageable) {
        Page<Project> projects = projectService.findByCategory(category, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("huntingFieldFilter")
    public ResponseEntity findByHuntingField(@RequestParam String huntingField, Pageable pageable) {
        Page<Project> projects = projectService.findByHuntingField(huntingField, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("regionFilter")
    public ResponseEntity findByRegion(@RequestParam String region, Pageable pageable) {
        Page<Project> projects = projectService.findByRegion(region, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("projectCategoryFilter")
    public ResponseEntity findByProjectCategory(@RequestParam String projectCategory, Pageable pageable) {
        Page<Project> projects = projectService.findByProjectCategory(projectCategory, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
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


    /*
    댓글 기능
     */

    // 댓글 등록
    @PostMapping("/{id}/comments")
    public Long saveComment(@RequestBody CommentSaveRequestDto requestDto, @PathVariable Long id) {
        return commentService.save(requestDto, id);
    }

    // 하나 불러오기 (detail)
    @GetMapping("/{id}/comments/{comment_id}")
    public CommentResponseDto findByCommentId(@PathVariable(name = "comment_id") Long comment_id) {
        return commentService.findById(comment_id);
    }

    // 전체 불러오기 (pagenation 적용) : request URL ex. - http://localhost:8080/projects?page=0&size=10&sort=id,desc
    @GetMapping("/{id}/comments")
    public ResponseEntity findAllComments(Pageable pageable) {
        Page<Comment> comments = commentService.findAll(pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // update
    @PutMapping("/{id}/comments/{comment_id}")
    public Long updateComment(@PathVariable(name = "comment_id") Long comment_id, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.update(comment_id, requestDto);
    }

    // delete
    @DeleteMapping("/{id}/comments/{comment_id}")
    public String deleteComment(@PathVariable(name = "comment_id") Long comment_id, @PathVariable(name = "id") Long project_id) {
        commentService.delete(comment_id, project_id);
        return "success";
    }

    // get count
    @GetMapping("/{id}/comments/count")
    public CommentCountResponseDto getCommentsCount(@PathVariable Long id) {
        return commentService.getCount(id);
    }

}
