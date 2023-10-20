package com.example.demo.Dto.ClothParsingDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

public class ClothResponseDto {

    @Builder
    @Data
    public static class ClothList {
        private List<ClothResponse> clothResponseList;
    }

    @Builder
    @Data
    public static class ClothResponse {
        private Optional<ColorResponse> colorResponse;
        private Optional<ClothTypeResponse> clothTypeResponse;
    }

    @Builder
    @Data
    public static class ColorResponse {
        private String colorNm;
        private int beginIndex;
        private int endIndex;
    }

    @Builder
    @Data
    public static class ClothTypeResponse {
        private String clothTypeNm;
        private int beginIndex;
        private int endIndex;
    }
}
