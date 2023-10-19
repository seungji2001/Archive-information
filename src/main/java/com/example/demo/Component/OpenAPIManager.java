package com.example.demo.Component;

import com.example.demo.Dto.HumanParsing;
import com.example.demo.Util.HumanParsingUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.antlr.v4.runtime.ParserInterpreter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class OpenAPIManager {
    String openApiURL = "http://aiopen.etri.re.kr:8000/HumanParsing";
    String accessKey = "e992244c-b81b-4913-964a-805c6b84799c";    // 발급받은 API Key
    String type = ".jpg";     // 이미지 파일 확장자
    String file = "/Users/seungjibaek/IdeaProjects/demo/src/main/resources/asset/person_detect_1.jpg";  	// 이미지 파일 경로
    String imageContents = "";
    Gson gson = new Gson();

    public void getHumanParsingApi() throws JsonProcessingException {
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        try {
            Path path = Paths.get(file);
            byte[] imageBytes = Files.readAllBytes(path);
            imageContents = Base64.getEncoder().encodeToString(imageBytes);
        } catch (
                IOException e) {
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

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        List<JsonNode> datasets = StreamSupport
                .stream(jsonNode.get("return_object").spliterator(), false)
                .toList();

        List<HumanParsing.Person> list = new ArrayList<>();
        for(int i = 0; i<datasets.size(); i++){
            System.out.println(datasets.get(i).get("num"));
            JsonNode jsonNode1 = datasets.get(i);
            HumanParsing.Person humanParsing = HumanParsing.Person.builder()
                    .num(jsonNode1.get("num").toString()).position(changeToList(jsonNode1.get("position").asText()))
                    .hatMask(changeToListList(jsonNode1.get("hat mask")))
                    .hatColor(changeToColor(jsonNode1.get("hat color").asText()))
                    .hairMask(changeToListList(jsonNode1.get("hair mask")))
                    .hairColor(changeToColor(jsonNode1.get("hair color").asText()))
                    .upclothMask(changeToListList(jsonNode1.get("upcloth mask")))
                    .upclothColor(changeToColor(jsonNode1.get("upcloth color").asText()))
                    .dressMask(changeToListList(jsonNode1.get("dress mask")))
                    .dressColor(changeToColor(jsonNode1.get("dress color").asText()))
                    .coatMask(changeToListList(jsonNode1.get("coat mask")))
                    .coatColor(changeToColor(jsonNode1.get("coat color").asText()))
                    .pantsMask(changeToListList(jsonNode1.get("pants mask")))
                    .pantsColor(changeToColor(jsonNode1.get("pants color").asText()))
                    .skirtMask(changeToListList(jsonNode1.get("skirt mask")))
                    .skirtColor(changeToColor(jsonNode1.get("skirt color").asText()))
                    .build();
            list.add(humanParsing);
        }

        System.out.println(list);
    }

    private Color changeToColor(String s) {
        s = s.substring(1, s.length() - 1);

        // Split the string into RGB values
        String[] rgbValues = s.split(", ");

        // Convert each RGB value to an integer
        int red = Integer.parseInt(rgbValues[0]);
        int green = Integer.parseInt(rgbValues[1]);
        int blue = Integer.parseInt(rgbValues[2]);

        // Create a Color object
        Color color = new Color(red, green, blue);

        return color;
    }

    private List<List<Integer>> changeToListList(JsonNode n) {
        System.out.println(n);

        List<List<Integer>> list = new ArrayList<>();
        if(n.isNull()){
            return list;
        }
        String s = n.asText();
        if(s.equals("None")){
            return list;
        }

        s = s.substring(2, s.length() - 2);

        // Split the string into inner lists
        String[] innerLists = s.split("] \\[");

        // Convert each inner list to a List<Integer>
        list = Arrays.stream(innerLists)
                .map(innerList -> Arrays.stream(innerList.replaceFirst("\\s+", "").split("\\s+"))
                        .map(l->Integer.parseInt(l.trim()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return list;
    }

    private List<Integer> changeToList(String s) {
        s = s.substring(1, s.length() - 1);

        // Split the string into an array of strings
        String[] stringArray = s.split(",");

        // Convert each string in the array to an integer
        List<Integer> list = Arrays.stream(stringArray)
                .map(l -> Integer.parseInt(l.trim()))
                .toList();

        // Print the list
        return list;
    }
}