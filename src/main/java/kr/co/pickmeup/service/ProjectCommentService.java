package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.comments.CommentRepository;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import kr.co.pickmeup.domain.users.User;
import kr.co.pickmeup.domain.users.UserRepository;
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
public class ProjectCommentService {

    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // create
    @Transactional
    public Long save(CommentSaveRequestDto requestDto, Long project_id) {
        Project project = projectRepository.findById(project_id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다. id =" + project_id));

        Comment comment = requestDto.toEntity();


        // user 연동
        String email = requestDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email=" + email));
        user.addComment(comment);

        // comment 저장
        project.addComment(comment);

        // comment 1 증가
        project.addCommentNum();

        return 1L;
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
    public void delete (Long id, Long project_id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        commentRepository.delete(comment);

        // project의 commentsNum 1 감소
        Project project = projectRepository.findById(project_id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다. id =" + project_id));
        project.subtractCommentNum();
    }

    // count
    public CommentCountResponseDto getCount(Long project_id) {

        Integer cnt = commentRepository.getCommentsCount(project_id);

        return new CommentCountResponseDto(project_id, cnt);
    }


}
