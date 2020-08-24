package kr.co.pickmeup.web.dto.ProjectsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequestDto {
    private String title;
    private String content;
    private String category;
    private String huntingField;
    private String region;
    private String projectCategory;
    private String[] tags;
    private String image;

    @Builder
    public ProjectUpdateRequestDto(String title, String content, String category, String huntingField, String region, String projectCategory, String[] tags, String image) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.huntingField = huntingField;
        this.region = region;
        this.projectCategory = projectCategory;
        this.tags = tags;

        if(image == null || image == "")
            this.image = null;
        else
            this.image = image;
    }
}
