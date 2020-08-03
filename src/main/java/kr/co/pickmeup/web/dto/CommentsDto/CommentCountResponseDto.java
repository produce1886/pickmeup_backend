package kr.co.pickmeup.web.dto.CommentsDto;

import lombok.Getter;

@Getter
public class CommentCountResponseDto {

    private Long project_id;
    private Integer cnt;

    public CommentCountResponseDto(Long project_id, Integer cnt) {
        this.project_id = project_id;
        this.cnt = cnt;
    }

}
