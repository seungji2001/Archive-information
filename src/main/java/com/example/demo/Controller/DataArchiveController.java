package com.example.demo.Controller;

import com.example.demo.Dto.DataArchiveDto.DataArchiveRequestDto;
import com.example.demo.Service.DataArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataArchiveController {

    @Autowired
    DataArchiveService dataArchiveService;

    @Value("${api.google.key}")
    String googleKey;
    @Value("${cx}")
    String cx;

    @PostMapping("/write/data")
    public ResponseEntity<Long> writeData(@RequestBody DataArchiveRequestDto.WriteData writeData){
        return ResponseEntity.ok().body(dataArchiveService.saveWriteData(writeData));
    }

    //wiki에서 단어 찾기
    //google 검색 제공
    @GetMapping("/search/{keyWord}")
    public void searchData(@PathVariable("keyWord") String keyWord) {
        dataArchiveService.searchData(keyWord,googleKey,cx);
    }
}
