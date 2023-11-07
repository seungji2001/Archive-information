package com.example.demo.Component;

import com.example.demo.Dto.NELinking.NELinkingResponseDto;
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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NELinkingManager {
    public List<NELinkingResponseDto.ResponseDto> getNeLinking(String accessKey, String contents) throws JsonProcessingException {
        String openApiURL = "http://aiopen.etri.re.kr:8000/NELinking";
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("contents", contents);

        request.put("argument", argument);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", accessKey);

        Gson gson = new Gson();
        HttpEntity<byte[]> entity = new HttpEntity<>(gson.toJson(request).getBytes(StandardCharsets.UTF_8), headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        JsonNode answer = jsonNode.get("return_object").findValue("mentions").findValue("definition");

        List<NELinkingResponseDto.ResponseDto> responseDtos = new ArrayList<>();
        for(int i = 0; i<jsonNode.get("return_object").size(); i++) {
            if (jsonNode.get("return_object").get(i).findValue("mentions").isEmpty()) {
                continue;
            }

           for (int a = 0; a < jsonNode.get("return_object").get(i).findValue("mentions").findValues("mention").size(); a++) {
                String mention = jsonNode.get("return_object").get(i).findValue("mentions").findValues("mention").get(a).asText();
                String definition = jsonNode.get("return_object").get(i).findValue("mentions").findValues("definition").get(a).asText();
                Long s_pos = jsonNode.get("return_object").get(i).findValue("mentions").findValues("s_pos").get(a).asLong();
                String url = jsonNode.get("return_object").get(i).findValue("mentions").findValues("url").get(a).asText();
                responseDtos.add(
                        NELinkingResponseDto.ResponseDto.builder()
                                .mention(mention)
                                .definition(definition)
                                .s_pos(s_pos)
                                .url(url)
                                .build()
                );
            }
        }
        return responseDtos;
    }
}
