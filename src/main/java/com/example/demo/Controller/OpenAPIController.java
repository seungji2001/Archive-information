package com.example.demo.Controller;

import com.example.demo.Component.*;
import com.example.demo.Dto.ChatBotDto.ChatBotResponseDto;
import com.example.demo.Dto.ClothParsingDto.ClothRequestDto;
import com.example.demo.Dto.ClothParsingDto.ClothResponseDto;
import com.example.demo.Dto.HumanParsing;
import com.example.demo.Dto.MRCServletDto.MRCServletRequestDto;
import com.example.demo.Dto.MRCServletDto.MRCServletResponseDto;
import com.example.demo.Dto.RecognitionDto.RecognitionResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RecognitionManager recognitionManager;
    private final MRCServletManager mrcServletManager;

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

    @GetMapping("recognition")
    public ResponseEntity<RecognitionResponseDto.questionResponse> getRecognition() throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok().body(recognitionManager.getRecognition(getKey));
    }

    @PostMapping("MRCServlet")
    public ResponseEntity<MRCServletResponseDto.ResponseDto> getMRCServlet(@RequestBody MRCServletRequestDto.RequestDto requestDto) throws UnsupportedEncodingException, JsonProcessingException {
        String passage = "루트비히 판 베토벤(독일어: Ludwig van Beethoven, 문화어: 루드위히 판 베토벤, 1770년 12월 17일 ~ 1827년 3월 26일)은 독일의 서양 고전 음악 작곡가이다. 독일의 본에서 태어났으며, 성인이 된 이후 거의 오스트리아 빈에서 살았다. 감기와 폐렴으로 인한 합병증으로 투병하다가 57세로 생을 마친 그는 고전주의와 낭만주의의 전환기에 활동한 주요 음악가이며, 작곡가로 널리 존경받고 있다. 음악의 성인(聖人) 또는 악성(樂聖)이라는 별칭으로 부르기도 한다. 가장 잘 알려진 작품 가운데에는 〈교향곡 5번〉, 〈교향곡 6번〉, 〈교향곡 9번〉, 〈비창 소나타〉, 〈월광 소나타〉 등이 있다.";            // 분석할 문단 데이터
        String question = "베토벤이 누구야";
        return ResponseEntity.ok().body(mrcServletManager.getMRCServlet(getKey,requestDto));
    }
}
