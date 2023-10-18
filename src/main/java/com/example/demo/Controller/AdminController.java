package com.example.demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AdminController {
    //세로운 멤버 등록페이지로 넘어가기
    @GetMapping(value = "/admin")
    public String registrationMemebr(){
        return "hi";
    }
}