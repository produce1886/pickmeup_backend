package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.comments.CommentRepository;
import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.portfolio.PortfolioRepository;
import kr.co.pickmeup.domain.users.User;
import kr.co.pickmeup.domain.users.UserRepository;
import kr.co.pickmeup.web.dto.CommentsDto.CommentResponseDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentSaveRequestDto;
import kr.co.pickmeup.web.dto.CommentsDto.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PortfolioCommentService {

    private final CommentRepository commentRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    // create
    @Transactional
    public Long save(CommentSaveRequestDto requestDto, Long project_id) {
        Portfolio portfolio = portfolioRepository.findById(project_id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다. id =" + project_id));

        Comment comment = requestDto.toEntity();

        // user 연동
        String email = requestDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email=" + email));
        user.addComment(comment);

        // comment 저장
        portfolio.addComment(comment);

        // comment 1 증가
        portfolio.addCommentNum();

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
        Portfolio portfolio = portfolioRepository.findById(project_id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다. id =" + project_id));
        portfolio.subtractCommentNum();
    }

}
