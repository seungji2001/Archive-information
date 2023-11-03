package com.example.demo.Component;

import com.example.demo.Dto.RecognitionDto.RecognitionRequestDto;
import com.example.demo.Dto.RecognitionDto.RecognitionResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class RecognitionManager {

    public RecognitionResponseDto.questionResponse getRecognition(String accessKey, RecognitionRequestDto.AudioFileRequest filename) throws JsonProcessingException, UnsupportedEncodingException {

        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition";
        String languageCode = "korean";     // 언어 코드
        String audioFilePath = "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/" + filename.getFilename();  // 녹음된 음성 파일 경로
        String audioContents = null;

        try {
            Path path = Paths.get(audioFilePath);
            byte[] audioBytes = Files.readAllBytes(path);
            audioContents = Base64.getEncoder().encodeToString(audioBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> request = new HashMap<>();
        Map<String, Object> argument = new HashMap<>();

        argument.put("language_code", languageCode);
        argument.put("audio", audioContents);

        request.put("argument", argument);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", accessKey);

        Gson gson = new Gson();
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        return RecognitionResponseDto.questionResponse.builder()
                .recognized(jsonNode.get("return_object").get("recognized").asText())
                .build();
    }
}
