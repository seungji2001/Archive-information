package com.example.demo.Component;

import com.example.demo.Dto.ChatBotDto.ChatBotResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.entity.mime.content.FileBody;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Component
public class VideoParseManager {
    public void getVideoParse(String accessKey) throws JsonProcessingException, UnsupportedEncodingException {

        String openApiURL = "http://aiopen.etri.re.kr:8000/VideoParse";
        String type = ".mp4";  	// 비디오 파일 확장자
        String file = "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/videoExample.mp4";  	// 비디오 파일 경로
        Gson gson = new Gson();

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();
        request.put("argument",argument);

        body.add("uploadfile", new FileSystemResource(new File(file)));
        body.add("json", gson.toJson(request));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", accessKey);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        System.out.println(jsonNode);
    }

    public void getVideoParseStatus(String accessKey) throws JsonProcessingException, UnsupportedEncodingException {

        String openApiURL = "http://aiopen.etri.re.kr:8000/VideoParse/status";
        Gson gson = new Gson();
        String file_id = "1697814974263.mp4";

        Map<String, Object> request = new HashMap<>();

        Map<String, Object> argument = new HashMap<>();
        argument.put("file_id", file_id);

        request.put("argument", argument);
        request.put("request_id", "reserved field");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", accessKey);

        System.out.println(gson.toJson(request));
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        System.out.println(jsonNode);
    }
}
