package com.example.demo.Dto.ChatBotDto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class ChatBotRequestDto {
    @NoArgsConstructor
    @Data
    public static class RequestQuestion {
        private String question;
    }
}
