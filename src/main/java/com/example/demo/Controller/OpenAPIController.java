package com.example.demo.Controller;

import com.example.demo.Component.OpenAPIManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenAPIController {
    private final OpenAPIManager openApiManager;

    @GetMapping("open-api")
    public void fetch() throws UnsupportedEncodingException {
        openApiManager.getHumanParsingApi();
    }
}