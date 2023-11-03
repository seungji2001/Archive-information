package com.example.demo.Controller;

import com.example.demo.Component.FileAccessManager;
import com.example.demo.Dto.MRCServletDto.MRCServletRequestDto;
import com.example.demo.Dto.MRCServletDto.MRCServletResponseDto;
import com.example.demo.Dto.RecordWordDto.RecordWordResponseDto;
import com.example.demo.Repository.RecordWordRepository;
import com.example.demo.Service.RecordWordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
public class RecordWordController {

    @Value("${api.key}")
    private String getKey;

    @Autowired
    RecordWordService recordWordService;

    @Autowired
    FileAccessManager fileAccessManager;

    @GetMapping("/recording")
    public ResponseEntity<Long> recording() throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok().body(recordWordService.recording(getKey));
    }

    @GetMapping("/paragraph/{recording_id}")
    public ResponseEntity<RecordWordResponseDto.GetParagrahResponseDto> getParagraph(@PathVariable("recording_id") Long recording_id){
        return ResponseEntity.ok().body(recordWordService.getParagraph(recording_id));
    }

    @PostMapping("/question/{recording_id}")
    public ResponseEntity<MRCServletResponseDto.ResponseDto> getAnswerByQuestion(@PathVariable("recording_id")Long recording_id, @RequestBody MRCServletRequestDto.RequestQuestionDto requestDto) throws JsonProcessingException {
        return ResponseEntity.ok().body(recordWordService.getAnswerByQuestion(recording_id, getKey, requestDto));
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // 업로드된 파일을 저장할 디렉토리 경로
        fileAccessManager.uploadFile(file);
        return "/textHtml";
    }
}
