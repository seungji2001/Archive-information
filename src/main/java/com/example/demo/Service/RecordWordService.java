package com.example.demo.Service;

import com.example.demo.Component.MRCServletManager;
import com.example.demo.Component.RecognitionManager;
import com.example.demo.Dto.MRCServletDto.MRCServletRequestDto;
import com.example.demo.Dto.MRCServletDto.MRCServletResponseDto;
import com.example.demo.Dto.RecognitionDto.RecognitionRequestDto;
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
    @Autowired
    MRCServletManager mrcServletManager;

    @Transactional
    public RecognitionResponseDto.questionResponse recording(String getKey, RecognitionRequestDto.AudioFileRequest filename) throws UnsupportedEncodingException, JsonProcessingException {
        RecognitionResponseDto.questionResponse questionResponse = recognitionManager.getRecognition(getKey, filename);
        return questionResponse;
    }

    @Transactional
    public RecordWordResponseDto.GetParagrahResponseDto getParagraph(Long recording_id){
        RecordWord recordWord = recordWordRepository.findById(recording_id).orElseThrow();
        return RecordWordResponseDto.GetParagrahResponseDto
                .builder()
                .paragraph(recordWord.getParagraph())
                .build();
    }

    @Transactional
    public MRCServletResponseDto.ResponseDto getAnswerByQuestion(String getKey, MRCServletRequestDto.RequestQuestionDto requestDto) throws JsonProcessingException {
//        RecordWord recordWord = recordWordRepository.findById(recording_id).orElseThrow();
        MRCServletRequestDto.RequestDto requestDto1 = MRCServletRequestDto.RequestDto.builder()
                .text(requestDto.getContent())
                .question(requestDto.getQuestion())
                .build();
        return mrcServletManager.getMRCServlet(getKey, requestDto1);
    }
}
