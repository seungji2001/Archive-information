package com.example.demo.Dto.ClothParsingDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ClothRequestDto {

    @Data
    @NoArgsConstructor
    public static class questionRequest{
        private String question;
    }
}
