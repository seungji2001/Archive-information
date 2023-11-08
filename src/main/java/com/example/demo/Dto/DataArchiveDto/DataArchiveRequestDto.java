package com.example.demo.Dto.DataArchiveDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class DataArchiveRequestDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WriteData{
        String data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SearchData{
        String data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AudioRelativeData{
        List<SearchData> relativeData;
    }
}
