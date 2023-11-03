package com.example.demo.Component;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileAccessManager {
    public void uploadFile(MultipartFile file){
        String uploadDir = "/Users/seungjibaek/IdeaProjects/Archive-information/src/main/resources/asset/";
        // 업로드된 파일 처리
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            File uploadedFile = new File(uploadDir, fileName);
            file.transferTo(uploadedFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
