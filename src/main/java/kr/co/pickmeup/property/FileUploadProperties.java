package kr.co.pickmeup.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="file")
public class FileUploadProperties {

    @Setter
    @Getter
    private String uploadDir;

}
