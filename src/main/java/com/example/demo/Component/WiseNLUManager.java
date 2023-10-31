package com.example.demo.Component;

import com.example.demo.Dto.ChatBotDto.ChatBotResponseDto;
import com.example.demo.Dto.ClothParsingDto.ClothRequestDto;
import com.example.demo.Dto.ClothParsingDto.ClothResponseDto;
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
import java.util.*;
import java.util.stream.StreamSupport;

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

    public ClothResponseDto.ClothList getWiseNLU(String accessKey, ClothRequestDto.questionRequest text) throws JsonProcessingException, UnsupportedEncodingException {

        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        String analysisCode = "ner";        // 언어 분석 코드
        Gson gson = new Gson();

        argument.put("analysis_code", analysisCode);
        argument.put("text", text.getQuestion());

        request.put("argument", argument);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", accessKey);

        HttpEntity<byte[]> entity = new HttpEntity<>(gson.toJson(request).getBytes(StandardCharsets.UTF_8), headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        List<JsonNode> datasets = StreamSupport
                .stream(jsonNode.get("return_object").get("sentence").findValue("NE").spliterator(), false)
                .toList();

        int begin = 0;
        int end = 0;
        String colorNm = "";
        List<ClothResponseDto.ClothResponse> clothResponseList = new ArrayList<>();
        for(int i = 0; i<datasets.size(); i++){
            //색상 + 옷 종류가 연달아 있을 경우 같은 곳에 넣는다
            JsonNode jsonNode1 = datasets.get(i);
            //type 확인하기
            String type = jsonNode1.get("type").asText();
            if(type.equals("TM_COLOR")){
                begin = jsonNode1.get("begin").asInt();
                end = jsonNode1.get("end").asInt();
                colorNm = jsonNode1.get("text").asText();
            }
            else if(type.equals("CV_CLOTHING")){
                //색상이 묘사된 옷일 경우 바로앞 인덱스에 저장되어 있을것이다.
                //이 경우 같이 저장한다
                if(end + 1 == jsonNode1.get("begin").asInt()){
                    Optional<ClothResponseDto.ColorResponse> colorResponse = Optional.of(
                            ClothResponseDto.ColorResponse.builder()
                            .colorNm(colorNm)
                            .beginIndex(begin)
                            .endIndex(end)
                            .build()
                    );

                    Optional<ClothResponseDto.ClothTypeResponse> clothTypeResponse = Optional.of(
                            ClothResponseDto.ClothTypeResponse.builder()
                            .clothTypeNm(jsonNode1.get("text").asText())
                            .beginIndex(jsonNode1.get("begin").asInt())
                            .endIndex(jsonNode1.get("end").asInt())
                            .build()
                    );

                    clothResponseList.add(
                            ClothResponseDto.ClothResponse.builder()
                            .colorResponse(colorResponse)
                            .clothTypeResponse(clothTypeResponse)
                            .build()
                    );
                }else{
                    //만약 이전 색상이 뒤의 옷을 꾸며주고 있지 않다면 따로 저장한다
                    Optional<ClothResponseDto.ColorResponse> colorResponse = Optional.of(
                            ClothResponseDto.ColorResponse.builder()
                                    .colorNm(colorNm)
                                    .beginIndex(begin)
                                    .endIndex(end)
                                    .build()
                    );

                    clothResponseList.add(
                            ClothResponseDto.ClothResponse.builder()
                                    .colorResponse(colorResponse)
                                    .clothTypeResponse(Optional.empty())
                                    .build()
                    );

                    Optional<ClothResponseDto.ClothTypeResponse> clothTypeResponse = Optional.of(
                            ClothResponseDto.ClothTypeResponse.builder()
                                    .clothTypeNm(jsonNode1.get("text").asText())
                                    .beginIndex(jsonNode1.get("begin").asInt())
                                    .endIndex(jsonNode1.get("end").asInt())
                                    .build()
                    );

                    clothResponseList.add(
                            ClothResponseDto.ClothResponse.builder()
                                    .colorResponse(Optional.empty())
                                    .clothTypeResponse(clothTypeResponse)
                                    .build()
                    );
                }
            }
        }

        return ClothResponseDto.ClothList.builder()
                .clothResponseList(clothResponseList)
                .build();
    }
}
