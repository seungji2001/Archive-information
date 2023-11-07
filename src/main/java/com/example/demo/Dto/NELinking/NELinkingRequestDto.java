package com.example.demo.Dto.NELinking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NELinkingRequestDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
   public static class RequestDto{
        String mention;
        String definition;
   }
}
