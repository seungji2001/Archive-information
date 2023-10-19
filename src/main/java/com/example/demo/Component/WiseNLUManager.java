package com.example.demo.Component;

import com.example.demo.Dto.ChatBotDto.ChatBotResponseDto;
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class WiseNLUManager {
    static public class Morpheme {
        final String text;
        final String type;
        Integer count;
        public Morpheme (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }
    static public class NameEntity {
        final String text;
        final String type;
        Integer count;
        public NameEntity (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }

    public void getWiseNLU(String accessKey) throws JsonProcessingException, UnsupportedEncodingException {

        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        String analysisCode = "ner";        // 언어 분석 코드
        String text = "";           // 분석할 텍스트 데이터

        Gson gson = new Gson();
        // 언어 분석 기술(문어)
        text += "흰 색 옷과 청바지를 입은 여성을 찾고싶다.";

        argument.put("analysis_code", analysisCode);
        argument.put("text", text);

        request.put("argument", argument);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", accessKey);

        HttpEntity<byte[]> entity = new HttpEntity<>(gson.toJson(request).getBytes(StandardCharsets.UTF_8), headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        System.out.println(jsonNode.get("return_object").get("sentence").findValue("NE"));
    }
}
