package com.example.demo.Dto.DataArchiveDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DataArchiveResponseDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class resultLink{
        String title;
        String link;
    }
}
