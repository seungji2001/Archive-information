package com.example.demo.Controller;

import com.example.demo.Repository.RecordWordRepository;
import com.example.demo.Service.RecordWordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class RecordWordController {

    @Value("${api.key}")
    private String getKey;

    @Autowired
    RecordWordService recordWordService;

    @GetMapping("/recording")
    public ResponseEntity<Long> recording() throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok().body(recordWordService.recording(getKey));
    }
}
