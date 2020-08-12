package kr.co.pickmeup.web;

import kr.co.pickmeup.service.FileUploadDownloadService;
import kr.co.pickmeup.web.dto.FileDto.FileUploadResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/profile")
public class FileUploadController {

    @Autowired
    private FileUploadDownloadService service;

    @PostMapping("/uploadFile")
    public FileUploadResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("upload 파일에 접근");
        String fileName = service.storeFile(file);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();

        return new FileUploadResponseDto(fileName, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<FileUploadResponseDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

}
