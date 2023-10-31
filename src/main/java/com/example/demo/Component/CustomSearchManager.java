package com.example.demo.Component;

import com.example.demo.Dto.DataArchiveDto.DataArchiveRequestDto;
import com.example.demo.Dto.MRCServletDto.MRCServletResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomSearchManager {
    public void customSearch(DataArchiveRequestDto.SearchData searchData, String googleKey, String cx){
        //    GET https://www.googleapis.com/customsearch/v1?key=INSERT_YOUR_API_KEY&cx=902104d01534b439e:omuauf_lfve&q=lectures
        String openApiURL = "https://www.googleapis.com/customsearch/v1?key="
                + googleKey
                + "&cx="
                +  cx
                + "&"
                + "q="
                + searchData.getData();

        System.out.println(openApiURL);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(openApiURL, HttpMethod.GET, entity, String.class);

        System.out.println(response);
    }
}
