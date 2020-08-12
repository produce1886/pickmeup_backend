package kr.co.pickmeup.web.dto.FileDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponseDto {

    private String fileName;
//    private String fileDownloadUri;
    private String fileType;
    private long size;

    @Builder
    public FileUploadResponseDto(String fileName, String fileType, long size) {
        this.fileName = fileName;
//        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

}
