package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.comments.CommentRepository;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import kr.co.pickmeup.web.dto.CommentsDto.CommentCountResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentSaveRequestDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentUpdateRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectResponseDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;

    // create
    @Transactional
    public Long save(CommentSaveRequestDto requestDto, Long project_id) {
        Project project = projectRepository.findById(project_id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다. id =" + project_id));

        project.addComment(requestDto.toEntity());

        return projectRepository.save(project).getId();
    }

    // read one
    @Transactional
    public CommentResponseDto findById (Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        return new CommentResponseDto(comment);
    }

    // read all
    @Transactional
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    // update
    @Transactional
    public Long update(Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        comment.update(requestDto.getContent());

        return id;
    }

    // delete
    @Transactional
    public void delete (Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        commentRepository.delete(comment);
    }

    // count
    public CommentCountResponseDto getCount(Long project_id) {

        Integer cnt = commentRepository.getCommentsCount(project_id);

        return new CommentCountResponseDto(project_id, cnt);
    }


}
