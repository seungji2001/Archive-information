package com.example.demo.Controller;

import com.example.demo.Dto.DataArchiveDto.DataArchiveRequestDto;
import com.example.demo.Dto.DataArchiveDto.DataArchiveResponseDto;
import com.example.demo.Service.DataArchiveService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.h2.util.json.JSONArray;
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
    @PostMapping("/search")
    public ResponseEntity<List<DataArchiveResponseDto.resultLink>> searchData(@RequestBody DataArchiveRequestDto.SearchData searchData) throws JsonProcessingException {
//        return ResponseEntity.ok().body(dataArchiveService.searchData(searchData,googleKey,cx));
        List<DataArchiveResponseDto.resultLink> resultLinks = new ArrayList<>();
        resultLinks.add(
                DataArchiveResponseDto.resultLink.builder()
                        .title(".NET을 통한 Python의 파일 형식 및 문서 처리")
                        .link("https://www.aspose.com/ko/products/python-net/")
                        .build()
        );
        resultLinks.add(
                DataArchiveResponseDto.resultLink.builder()
                        .title("파이썬(Python) 바로 알기 – 특징, 장점, 활용 사례 | 가비아 라이브러리")
                        .link("https://library.gabia.com/contents/9256/")
                        .build()
        );
        resultLinks.add(
                DataArchiveResponseDto.resultLink.builder()
                        .title("Python 세계에서 Ansys의 강력한 기능에 액세스")
                        .link("https://www.ansys.com/ko-kr/blog/accessing-ansys-from-python")
                        .build()
        );
        return ResponseEntity.ok().body(resultLinks);
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
