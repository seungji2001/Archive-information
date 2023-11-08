package com.example.demo.Controller;

import com.example.demo.Component.NELinkingManager;
import com.example.demo.Component.TranslateManager;
import com.example.demo.Dto.DataArchiveDto.DataArchiveRequestDto;
import com.example.demo.Dto.DataArchiveDto.DataArchiveResponseDto;
import com.example.demo.Dto.NELinking.NELinkingRequestDto;
import com.example.demo.Dto.NELinking.NELinkingResponseDto;
import com.example.demo.Service.DataArchiveService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.h2.util.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DataArchiveController {

    @Autowired
    DataArchiveService dataArchiveService;
    @Autowired
    NELinkingManager neLinkingManager;
    @Autowired
    TranslateManager translateManager;
    @Value("${api.key}")
    private String getKey;

    @Value("${api.google.key}")
    String googleKey;
    @Value("${cx}")
    String cx;
    @Value("${client.id}")
    String client_id;
    @Value("${secret.key}")
    String secret_key;

    @PostMapping("/write/data")
    public ResponseEntity<Long> writeData(@RequestBody DataArchiveRequestDto.WriteData writeData){
        return ResponseEntity.ok().body(dataArchiveService.saveWriteData(writeData));
    }

    //wiki에서 단어 찾기
    //google 검색 제공
    @PostMapping("/search")
    public ResponseEntity<List<DataArchiveResponseDto.resultLink>> searchData(@RequestBody DataArchiveRequestDto.SearchData searchData) throws JsonProcessingException {
        return ResponseEntity.ok().body(dataArchiveService.searchData(searchData,googleKey,cx));
//        List<DataArchiveResponseDto.resultLink> resultLinks = new ArrayList<>();
//        resultLinks.add(
//                DataArchiveResponseDto.resultLink.builder()
//                        .title(".NET을 통한 Python의 파일 형식 및 문서 처리")
//                        .link("https://www.aspose.com/ko/products/python-net/")
//                        .build()
//        );
//        resultLinks.add(
//                DataArchiveResponseDto.resultLink.builder()
//                        .title("파이썬(Python) 바로 알기 – 특징, 장점, 활용 사례 | 가비아 라이브러리")
//                        .link("https://library.gabia.com/contents/9256/")
//                        .build()
//        );
//        resultLinks.add(
//                DataArchiveResponseDto.resultLink.builder()
//                        .title("Python 세계에서 Ansys의 강력한 기능에 액세스")
//                        .link("https://www.ansys.com/ko-kr/blog/accessing-ansys-from-python")
//                        .build()
//        );
//        return ResponseEntity.ok().body(resultLinks);
    }

    @PostMapping("/search/relativeData")
    public ResponseEntity<List<DataArchiveResponseDto.resultLink>> searchData(@RequestBody DataArchiveRequestDto.AudioRelativeData audioRelativeData) throws JsonProcessingException {
        return ResponseEntity.ok().body(dataArchiveService.searchRelativeData(audioRelativeData,googleKey,cx));
//        List<DataArchiveResponseDto.resultLink> resultLinks = new ArrayList<>();
//        resultLinks.add(
//                DataArchiveResponseDto.resultLink.builder()
//                        .title(".NET을 통한 Python의 파일 형식 및 문서 처리")
//                        .link("https://www.aspose.com/ko/products/python-net/")
//                        .build()
//        );
//        resultLinks.add(
//                DataArchiveResponseDto.resultLink.builder()
//                        .title("파이썬(Python) 바로 알기 – 특징, 장점, 활용 사례 | 가비아 라이브러리")
//                        .link("https://library.gabia.com/contents/9256/")
//                        .build()
//        );
//        resultLinks.add(
//                DataArchiveResponseDto.resultLink.builder()
//                        .title("Python 세계에서 Ansys의 강력한 기능에 액세스")
//                        .link("https://www.ansys.com/ko-kr/blog/accessing-ansys-from-python")
//                        .build()
//        );
//        return ResponseEntity.ok().body(resultLinks);
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/nelinking")
    public ResponseEntity<List<NELinkingResponseDto.ResponseDto>> getNELinking(@RequestBody NELinkingRequestDto.RequestDto requestDto) throws JsonProcessingException {
        String contents = "노벨 물리학상( - 物理學賞, 스웨덴어: Nobelpriset i fysik, 영어: Nobel Prize in Physics)은 6개 분야의 노벨상 중 하나로, 1년에 한 번 스웨덴 왕립 과학원에 의해 수여된다. 첫 번째 노벨 물리학상은 1901년, 엑스선을 발견한 독일의 빌헬름 콘라트 뢴트겐에게 수여되었다. 이 상은 노벨 재단이 주관하며, 이 상을 수상하는 것은 물리학계에서 최고의 영예로 꼽힌다. 노벨 물리학상은 알프레드 노벨의 사망일인 12월 10일에 스톡홀름에서 수여된다. 2007년의 노벨 물리학상은 프랑스의 알베르 페르와 독일의 페터 그륀베르크가 거대 자기저항의 발견에 대한 공로로 공동 수상하였고, 상금인 1천만 스웨덴 크로나를 나누어 가졌다. 노벨 물리학상은 사람들이 그 과학자의 업적의 중요성을 알기까지의 시간이 걸리기 때문에, 다른 한 편으로는 시간과의 싸움이라고 말할 수 있다. 예를 들어 1983년 노벨 물리학상 수상자인 수브라마니안 찬드라세카르의 경우 그 이론은 이미 1930년에 이미 발표하였지만 사람들에게 인정을 받지 못하여 50여년이 지나서야 상을 받을 수가 있었다. 또한 2013년 노벨 물리학상 수상자인 피터 힉스와 프랑수아 앙글레르의 경우 그의 이론을 검증할 수 있게 되는 과학기술이 발전하기 까지 오랜 시간이 걸려, 49년 뒤 그의 업적이 사실로 확인되어 노벨상을 받게 되었다. 그래서 많은 이론과 발견이 사람들에게 중요성을 인정받았지만, 그 이론이나 발견을 발표한 과학자가 이미 죽어버렸기에 노벨 물리학상을 받지 못하는 안타까운 경우도 있다.";

        return ResponseEntity.ok().body(neLinkingManager.getNeLinking(getKey, requestDto.getContent()));
    }

    @PostMapping("/translate/{lan}")
    public ResponseEntity<String> getTranslate(@RequestBody String question, @PathVariable("lan")String lan) throws MalformedURLException {
        return ResponseEntity.ok().body(translateManager.translate(client_id, secret_key, question, lan));
    }
}
