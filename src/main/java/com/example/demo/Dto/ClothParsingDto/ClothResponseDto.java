package com.example.demo.Dto.ClothParsingDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ClothResponseDto {

    @Builder
    @Data
    public static class ClothList {
        private List<ClothResponse> clothResponseList;
    }

    @Builder
    @Data
    public static class ClothResponse {
        private ColorResponse colorResponse;
        private ClothTypeResponse clothTypeResponse;
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
