package com.example.demo.Dto.MRCServletDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MRCServletResponseDto {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ResponseDto{
        private String answer;
    }
}
