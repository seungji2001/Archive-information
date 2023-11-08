package com.example.demo.Component;

import com.example.demo.Dto.DataArchiveDto.DataArchiveRequestDto;
import com.example.demo.Dto.DataArchiveDto.DataArchiveResponseDto;
import com.example.demo.Dto.MRCServletDto.MRCServletResponseDto;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomSearchManager {
    public List<DataArchiveResponseDto.resultLink> customSearch(DataArchiveRequestDto.SearchData searchData, String googleKey, String cx, Boolean total) throws JsonProcessingException {
        List<DataArchiveResponseDto.resultLink> totalJsonNodes = new ArrayList<>();
        int end;
        if(total){
            end = 50;
        }else{
            end = 5;
        }
        for(int i = 1; i<=1; i++){
            String openApiURL = "https://www.googleapis.com/customsearch/v1?key="
                    + googleKey
                    + "&cx="
                    +  cx
                    + "&"
                    + "q="
                    + searchData.getData()
                    + "&start="
                    + i*10;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json; charset=UTF-8");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.GET, entity, String.class);
            System.out.println(response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            System.out.println(jsonNode.size());
            for(int a = 0; a<jsonNode.get("items").size(); a++){
                String link = jsonNode.get("items").get(a).get("link").asText();
                String title = jsonNode.get("items").get(a).get("title").asText();
//                if(title.contains(searchData.getData())){
                    totalJsonNodes.add( DataArchiveResponseDto.resultLink.builder()
                            .link(link)
                            .title(title)
                            .build()
                    );
//                }
            }
//            List<DataArchiveResponseDto.resultLink> jsonNodes = jsonNode.get("items").findValues("link").stream()
//                    .map(JsonNode::asText)
//                    .filter(text -> text.contains(searchData.getData()))
//                    .map(
//                            text -> {
//                                return DataArchiveResponseDto.resultLink.builder()
//                                        .link(text)
//                                        .build();
//                            }
//                    )
//                    .toList();
//            if(!jsonNodes.isEmpty())
//                totalJsonNodes.addAll(jsonNodes);
        }

        return totalJsonNodes;
    }
}
