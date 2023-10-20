package com.example.demo.Controller;

import com.example.demo.Component.OpenAPIManager;
import com.example.demo.Component.VideoParseManager;
import com.example.demo.Component.WiseNLUManager;
import com.example.demo.Dto.ChatBotDto.ChatBotResponseDto;
import com.example.demo.Dto.ClothParsingDto.ClothRequestDto;
import com.example.demo.Dto.ClothParsingDto.ClothResponseDto;
import com.example.demo.Dto.HumanParsing;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenAPIController {
    private final OpenAPIManager openApiManager;
    private final WiseNLUManager wiseNLUManager;
    private final VideoParseManager videoParseManager;

    @Value("${api.key}")
    private String getKey;

    @GetMapping("humanParse")
    public ResponseEntity<HumanParsing.ResponseDto> getHumanParsingApi() throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok().body(openApiManager.getHumanParsingApi("http://aiopen.etri.re.kr:8000/HumanParsing", getKey
                ,".jpg", "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/person_detect_1.jpg"));
    }

    @GetMapping("wikiQA")
    public ResponseEntity<ChatBotResponseDto.ResponseAnswer> getWikiQA() throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok(openApiManager.getWikiQA("http://aiopen.etri.re.kr:8000/WikiQA", getKey,"irqa","김구가 누구야?"));
    }

    @PostMapping("wiseNLU")
    public ResponseEntity<ClothResponseDto.ClothList> getWiseNLU(@RequestBody ClothRequestDto.questionRequest questionRequest) throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok().body(wiseNLUManager.getWiseNLU(getKey, questionRequest));
    }

    @GetMapping("videoParse")
    public void getVideoParse() throws UnsupportedEncodingException, JsonProcessingException {
        videoParseManager.getVideoParse(getKey);
    }

    @GetMapping("videoParse/status")
    public void getVideoParseStatus() throws UnsupportedEncodingException, JsonProcessingException {
        videoParseManager.getVideoParseStatus(getKey);
    }
}
