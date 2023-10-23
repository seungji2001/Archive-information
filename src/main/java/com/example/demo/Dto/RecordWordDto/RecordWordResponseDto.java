package com.example.demo.Dto.RecordWordDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RecordWordResponseDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class GetParagrahResponseDto{
        String paragraph;
    }
}
