package kr.co.pickmeup.web.dto.CommentsDto;

import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.projects.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private String content;
    private String email;

    @Builder
    public CommentSaveRequestDto(String content, String email) {
        this.content = content;
        this.email = email;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .email(email)
                .build();
    }

}
