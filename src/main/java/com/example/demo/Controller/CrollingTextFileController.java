package com.example.demo.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class CrollingTextFileController {
    @PostMapping("/upload/text")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException, IOException {
        // Process the file content
        String text = new String(file.getBytes(), StandardCharsets.UTF_8);

        // Perform crawling operations on the text

        System.out.println(text);
        // Pass the crawling result to the view
       return text;
    }
}
