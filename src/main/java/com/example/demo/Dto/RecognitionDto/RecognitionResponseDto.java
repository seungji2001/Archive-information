package com.example.demo.Dto.RecognitionDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RecognitionResponseDto {
    @Data
    @Builder
    public static class questionRequest{
        private String recognized;
    }
}
