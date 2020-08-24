package kr.co.pickmeup.web.dto.TagsDto;

import kr.co.pickmeup.domain.tags.Tag;
import lombok.Getter;

@Getter
public class TagScoreResponseDto {

    private Long id;
    private String name;
    private double score;

    public TagScoreResponseDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.score = tag.getScore();
    }

}
