package com.example.demo.Dto.ChatBotDto;

import lombok.Builder;
import lombok.Data;

public class ChatBotResponseDto {
    @Builder
    @Data
    public static class ResponseAnswer {
        private String answer;
    }
}
