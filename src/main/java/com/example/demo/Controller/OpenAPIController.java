package com.example.demo.Controller;

import com.example.demo.Component.OpenAPIManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenAPIController {
    private final OpenAPIManager openApiManager;

    @Value("${api.key}")
    private String getKey;
    @GetMapping("open-api")
    public void getHumanParsingApi() throws UnsupportedEncodingException, JsonProcessingException {
        openApiManager.getHumanParsingApi("http://aiopen.etri.re.kr:8000/HumanParsing", getKey
                ,".jpg", "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/person_detect_1.jpg");
    }

    @GetMapping("open-api/wikiQA")
    public void getWikiQA() throws UnsupportedEncodingException, JsonProcessingException {
        openApiManager.getWikiQA("http://aiopen.etri.re.kr:8000/WikiQA", getKey,"irqa","김구가 누구야?"
                );
    }
}
