package com.example.demo.Service;

import com.example.demo.Component.RecognitionManager;
import com.example.demo.Dto.RecognitionDto.RecognitionResponseDto;
import com.example.demo.Dto.RecordWordDto.RecordWordResponseDto;
import com.example.demo.Repository.RecordWordRepository;
import com.example.demo.domain.RecordWord;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class RecordWordService {

    @Autowired
    RecognitionManager recognitionManager;
    @Autowired
    RecordWordRepository recordWordRepository;

    @Transactional
    public Long recording(String getKey) throws UnsupportedEncodingException, JsonProcessingException {
        RecognitionResponseDto.questionResponse questionResponse = recognitionManager.getRecognition(getKey);
        RecordWord recordWord = RecordWord.builder()
                .paragraph(questionResponse.getRecognized())
                .build();
        return recordWordRepository.save(recordWord).getId();
    }

    @Transactional
    public RecordWordResponseDto.GetParagrahResponseDto getParagraph(Long recording_id){
        RecordWord recordWord = recordWordRepository.findById(recording_id).orElseThrow();
        return RecordWordResponseDto.GetParagrahResponseDto
                .builder()
                .paragraph(recordWord.getParagraph())
                .build();
    }
}
