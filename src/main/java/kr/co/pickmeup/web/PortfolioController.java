package kr.co.pickmeup.web;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.service.PortfolioCommentService;
import kr.co.pickmeup.service.PortfolioService;
import kr.co.pickmeup.web.dto.CommentsDto.CommentCountResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentSaveRequestDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentUpdateRequestDto;
import kr.co.pickmeup.web.dto.PortfolioDto.*;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectPaginationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(value="/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioCommentService portfolioCommentService;

    // 프로젝트 save
    @PostMapping()
    public ResponseEntity save(@RequestBody PortfolioSaveRequestDto requestDto) {
        portfolioService.save(requestDto);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    // 하나 불러오기 (detail)
    @GetMapping("/{id}")
    public PortfolioResponseDto findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PortfolioUpdateRequestDto requestDto) {
        Long portfolioId = portfolioService.update(id, requestDto);
        return new ResponseEntity<> (portfolioId, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        portfolioService.delete(id);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    // 필터링, 전체 불러오기
    @PostMapping("list")
    public ResponseEntity findAll(Pageable pageable, @RequestBody PortfolioPaginationRequestDto requestDto) {
        PortfolioListResponseList list = portfolioService.findAll(requestDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    /*
    댓글 기능
     */

    // 댓글 등록
    @PostMapping("/{id}/comments")
    public Long saveComment(@RequestBody CommentSaveRequestDto requestDto, @PathVariable Long id) {
        return portfolioCommentService.save(requestDto, id);
    }

    // 하나 불러오기 (detail)
    @GetMapping("/{id}/comments/{comment_id}")
    public CommentResponseDto findByCommentId(@PathVariable(name = "comment_id") Long comment_id) {
        return portfolioCommentService.findById(comment_id);
    }

    // 전체 불러오기 (pagenation 적용) : request URL ex. - http://localhost:8080/projects?page=0&size=10&sort=id,desc
    @GetMapping("/{id}/comments")
    public ResponseEntity findAllComments(Pageable pageable) {
        Page<Comment> comments = portfolioCommentService.findAll(pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // update
    @PutMapping("/{id}/comments/{comment_id}")
    public Long updateComment(@PathVariable(name = "comment_id") Long comment_id, @RequestBody CommentUpdateRequestDto requestDto) {
        return portfolioCommentService.update(comment_id, requestDto);
    }

    // delete
    @DeleteMapping("/{id}/comments/{comment_id}")
    public String deleteComment(@PathVariable(name = "comment_id") Long comment_id, @PathVariable(name = "id") Long project_id) {
        portfolioCommentService.delete(comment_id, project_id);
        return "success";
    }

}
