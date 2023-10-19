package com.example.demo.Util;
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HumanParsingUtil {
    static public void main(String[] args) {
//        humanParsing();
        testGrammar();
    }

    private static void testGrammar() {
        List<List<Integer>> list = new ArrayList<>();
        String s = "[[ 91 129] [ 90 130] [ 85 130] [ 84 131] [ 80 131] [ 79 132] [ 72 132] [ 72 133] [ 73 134] [ 73 135] [ 72 136] [ 71 136] [ 70 137] [ 69 137] [ 68 138] [ 67 138] [ 66 139] [ 64 139] [ 63 140] [ 60 140] [ 59 139] [ 56 139] [ 55 138] [ 53 138] [ 52 137] [ 51 137] [ 50 136] [ 49 136] [ 47 134] [ 46 134] [ 45 133] [ 43 133] [ 42 134] [ 41 134] [ 40 135] [ 38 135] [ 37 136] [ 33 136] [ 32 137] [ 31 137] [ 30 138] [ 29 138] [ 28 139] [ 27 139] [ 23 143] [ 23 147] [ 24 148] [ 24 162] [ 23 163] [ 23 178] [ 22 179] [ 22 186] [ 21 187] [ 21 190] [ 20 191] [ 20 193] [ 19 194] [ 19 196] [ 18 197] [ 18 198] [ 17 199] [ 17 200] [ 15 202] [ 15 203] [ 14 204] [ 14 205] [ 13 206] [ 13 207] [ 11 209] [ 11 210] [ 10 211] [ 10 213] [  9 214] [  9 216] [  8 217] [  8 218] [  9 219] [  9 221] [ 20 221] [ 21 222] [ 24 222] [ 26 224] [ 26 229] [ 47 229] [ 47 221] [ 48 220] [ 48 219] [ 49 218] [ 49 217] [ 50 216] [ 50 215] [ 52 213] [ 52 211] [ 53 210] [ 53 208] [ 54 207] [ 54 205] [ 55 204] [ 55 203] [ 56 202] [ 56 201] [ 57 200] [ 57 198] [ 58 197] [ 58 196] [ 59 195] [ 59 191] [ 60 190] [ 60 184] [ 61 183] [ 61 177] [ 62 176] [ 62 171] [ 61 170] [ 61 167] [ 60 166] [ 60 157] [ 62 155] [ 63 155] [ 67 159] [ 67 160] [ 68 161] [ 68 162] [ 69 163] [ 70 163] [ 77 170] [ 78 170] [ 82 174] [ 82 175] [ 83 176] [ 83 177] [ 84 178] [ 84 183] [ 83 184] [ 83 187] [ 82 188] [ 82 191] [ 81 192] [ 81 193] [ 80 194] [ 80 195] [ 78 197] [ 78 199] [ 77 200] [ 77 204] [ 76 205] [ 76 210] [ 75 211] [ 75 214] [ 74 215] [ 74 218] [ 73 219] [ 73 229] [106 229] [106 221] [107 220] [107 218] [108 217] [108 214] [109 213] [109 211] [110 210] [110 208] [111 207] [111 206] [112 205] [112 201] [113 200] [113 196] [114 195] [114 192] [115 191] [115 188] [116 187] [116 184] [117 183] [117 181] [118 180] [118 178] [119 177] [119 175] [120 174] [120 172] [121 171] [121 168] [122 167] [122 163] [121 162] [121 156] [120 155] [120 153] [118 151] [118 150] [113 145] [112 145] [109 142] [108 142] [104 138] [103 138] [ 99 134] [ 98 134] [ 94 130] [ 93 130] [ 92 129]]";
        s = s.substring(2, s.length() - 2);
        String[] innerLists = s.split("] \\[");

        // Convert each inner list to a List<Integer>
        list = Arrays.stream(innerLists)
                .map(innerList -> Arrays.stream(innerList.replaceFirst("\\s+","").split("\\s+"))
                        .map(l->Integer.parseInt(l.trim()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        System.out.println(list);
    }

    public static void humanParsing() {

        String openApiURL = "http://aiopen.etri.re.kr:8000/HumanParsing";
        String accessKey = "e992244c-b81b-4913-964a-805c6b84799c";    // 발급받은 API Key
        String type = ".jpg";     // 이미지 파일 확장자
        String file = "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/person_detect_1.jpg";    // 이미지 파일 경로
        String imageContents = "";
        Gson gson = new Gson();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        try {
            Path path = Paths.get(file);
            byte[] imageBytes = Files.readAllBytes(path);
            imageContents = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        argument.put("type", type);
        argument.put("file", imageContents);

        request.put("argument", argument);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", accessKey);

        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.POST, entity, String.class);

        System.out.println("[responseCode] " + response.getStatusCode());
        System.out.println("[responBody]");
        System.out.println(response.getBody());
    }

}