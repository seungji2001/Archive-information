//package com.example.demo.Component;
//
//import com.google.gson.Gson;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Map;
//
//@Component
//public class OpenAPIManager {
//    String openApiURL = "http://aiopen.etri.re.kr:8000/HumanParsing";
//    String accessKey = "e992244c-b81b-4913-964a-805c6b84799c";    // 발급받은 API Key
//    String type = ".jpg";     // 이미지 파일 확장자
//    String file = "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/person_detect_1";  	// 이미지 파일 경로
//    String imageContents = "";
//    Gson gson = new Gson();
//
//
//    private String makeUrl() throws UnsupportedEncodingException {
//        return openApiURL +
//                apiUri +
//                serviceKey +
//                defaultQueryParam +
//                numOfRows +
//                areaCode +
//                contentTypeId;
//    }
//
//    public ResponseEntity<?> fetch() throws UnsupportedEncodingException {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
//        ResponseEntity<Map> resultMap = restTemplate.exchange(makeUrl(), HttpMethod.GET, entity, Map.class);
//        System.out.println(resultMap.getBody());
//        return resultMap;
//
//    }
//}