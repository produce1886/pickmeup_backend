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
    private String author;

    @Builder
    public CommentSaveRequestDto(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .author(author)
                .build();
    }

}
