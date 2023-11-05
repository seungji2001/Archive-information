package com.example.demo.Controller;

import com.example.demo.Dto.DataArchiveDto.DataArchiveRequestDto;
import com.example.demo.Dto.DataArchiveDto.DataArchiveResponseDto;
import com.example.demo.Service.DataArchiveService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Controller
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
    @GetMapping("/search")
    public ResponseEntity<List<DataArchiveResponseDto.resultLink>> searchData(@RequestBody DataArchiveRequestDto.SearchData searchData) throws JsonProcessingException {
//        return ResponseEntity.ok().body(dataArchiveService.searchData(searchData,googleKey,cx));
        List<DataArchiveResponseDto.resultLink> resultLinks = new ArrayList<>();
        resultLinks.add(
                DataArchiveResponseDto.resultLink.builder()
                        .link("https://d2.naver.com/helloworld/8257914")
                        .build()
        );
        resultLinks.add(
                DataArchiveResponseDto.resultLink.builder()
                        .link("https://www.kci.go.kr/kciportal/ci/sereArticleSearch/ciSereArtiView.kci?sereArticleSearchBean.artiId=ART002617092")
                        .build()
        );
        resultLinks.add(
                DataArchiveResponseDto.resultLink.builder()
                        .link("https://m.blog.naver.com/sleep0615/222235319453")
                        .build()
        );
        return ResponseEntity.ok().body(resultLinks);
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
