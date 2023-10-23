package com.example.demo.Component;

import com.example.demo.Dto.MRCServletDto.MRCServletRequestDto;
import com.example.demo.Dto.MRCServletDto.MRCServletResponseDto;
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
public class MRCServletManager {
    public MRCServletResponseDto.ResponseDto getMRCServlet(String accessKey, MRCServletRequestDto.RequestDto requestDto) throws JsonProcessingException {
        String openApiURL = "http://aiopen.etri.re.kr:8000/MRCServlet";


        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("question", requestDto.getQuestion());
        argument.put("passage", requestDto.getText());

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

        return MRCServletResponseDto.ResponseDto.builder()
                .answer(jsonNode.get("return_object").get("MRCInfo").get("answer").asText())
                .build();

    }
}
