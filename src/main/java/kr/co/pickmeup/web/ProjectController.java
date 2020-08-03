package kr.co.pickmeup.web;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import kr.co.pickmeup.service.CommentService;
import kr.co.pickmeup.service.ProjectService;
import kr.co.pickmeup.web.dto.CommentsDto.CommentCountResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentSaveRequestDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentUpdateRequestDto;
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

    private final CommentService commentService;

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


    /*
    댓글 기능
     */

    // 댓글 등록
    @PostMapping("/{id}/comments")
    public Long saveComment(@PathVariable Long id, @RequestBody CommentSaveRequestDto requestDto) {
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
        return new ResponseEntity<> (comments, HttpStatus.OK);
    }

    // update
    @PutMapping("/{id}/comments/{comment_id}")
    public Long updateComment(@PathVariable(name = "comment_id") Long comment_id, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.update(comment_id, requestDto);
    }

    // delete
    @DeleteMapping("/{id}/comments/{comment_id}")
    public String deleteComment(@PathVariable(name = "comment_id") Long comment_id) {
        commentService.delete(comment_id);
        return "success";
    }

    // get count
    @GetMapping("/{id}/comments/count")
    public CommentCountResponseDto getCommentsCount(@PathVariable Long id) {
        return commentService.getCount(id);
    }

}
