package com.example.demo.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Component

    // 네이버 기계번역 (Papago SMT) API 예제
    public class TranslateManager {
//        public static void main(String[] args) {
//            String clientId = "5V3ZWI6y0PKAAYWOYSno";
//            String clientSecret = "u7OtyNxnNn";
//            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
//            String text;
//            text = URLEncoder.encode("안녕하세요. 오늘 기분은 어떻습니까?", StandardCharsets.UTF_8);
//
//            Map<String, String> requestHeaders = new HashMap<>();
//            requestHeaders.put("X-Naver-Client-Id", clientId);
//            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
//
//            String responseBody = post(apiURL, requestHeaders, text);
//
//            System.out.println(responseBody);
//        }
//
//        private static String post(String apiUrl, Map<String, String> requestHeaders, String text) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAll(requestHeaders);
//
//            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//            body.add("source", "ko");
//            body.add("target", "en");
//            body.add("text", text);
//
//            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
//
//            RestTemplate restTemplate = new RestTemplate();
//
//            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
//
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                System.out.println(requestEntity.getBody());
//                return responseEntity.getBody();
//            } else {
//                // Handle error response
//                return null;
//            }
//        }

        public String translate(String clientId, String clientSecret, String question, String lan) throws MalformedURLException {
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            String text;
            try {
                text = URLEncoder.encode(question, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("인코딩 실패", e);
            }

            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);

            String responseBody = post(apiURL, requestHeaders, text, lan);

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
            String translatedText = jsonObject.get("message")
                    .getAsJsonObject()
                    .get("result")
                    .getAsJsonObject()
                    .get("translatedText")
                    .getAsString();

            return translatedText;

        }

        private static String post(String apiUrl, Map<String, String> requestHeaders, String text, String lan) throws MalformedURLException {
            HttpURLConnection con = connect(apiUrl);
            String postParams = null; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
            if(lan.equals("en"))
                postParams = "source=en&target=ko&text=" + text;
            else if(lan.equals("ko"))
                postParams = "source=ko&target=en&text=" + text;
            try {
                con.setRequestMethod("POST");
                for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }

                con.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.write(postParams.getBytes());
                    wr.flush();
                }

                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                    return readBody(con.getInputStream());
                } else {  // 에러 응답
                    return readBody(con.getErrorStream());
                }
            } catch (IOException e) {
                throw new RuntimeException("API 요청과 응답 실패", e);
            } finally {
                con.disconnect();
            }
        }

        private static HttpURLConnection connect(String apiUrl) throws MalformedURLException {
            try {
                URL url = new URL(apiUrl);
                return (HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static String readBody(InputStream body){
            InputStreamReader streamReader = new InputStreamReader(body);

            try (BufferedReader lineReader = new BufferedReader(streamReader)) {
                StringBuilder responseBody = new StringBuilder();

                String line;
                while ((line = lineReader.readLine()) != null) {
                    responseBody.append(line);
                }

                return responseBody.toString();
            } catch (IOException e) {
                throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
            }
        }
    }



