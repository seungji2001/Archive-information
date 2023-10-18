package com.example.demo.Component;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class OpenAPIManager {
    String openApiURL = "http://aiopen.etri.re.kr:8000/HumanParsing";
    String accessKey = "e992244c-b81b-4913-964a-805c6b84799c";    // 발급받은 API Key
    String type = ".jpg";     // 이미지 파일 확장자
    String file = "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/person_detect_1.jpg";  	// 이미지 파일 경로
    String imageContents = "";
    Gson gson = new Gson();

    public void getHumanParsingApi(){
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        try {
            Path path = Paths.get(file);
            byte[] imageBytes = Files.readAllBytes(path);
            imageContents = Base64.getEncoder().encodeToString(imageBytes);
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        argument.put("type", type);
        argument.put("file", imageContents);

        request.put("argument", argument);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", accessKey);

        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, entity, String.class);

        System.out.println("[responseCode] " + response.getStatusCode());
        System.out.println("[responBody]");
        System.out.println(response.getBody());
    }
}