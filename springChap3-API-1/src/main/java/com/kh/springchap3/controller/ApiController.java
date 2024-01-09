package com.kh.springchap3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class ApiController {

    @GetMapping("/api/get")
    public String allowBasic() {
        StringBuilder result = new StringBuilder();

        try {
            String apiUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
            String apiKey = "ZTckEfSDn30pQA8mkLQt0gcRfTMDnv9Cd685DihsfQ9tnqxwYucJfQ72YCuQ9mkvW7re+ZqXLy/hI8+cqxjUcA==";
            String pageNo = "1";
            String numOfRows = "10";
            String dataType = "json";
            String stnId = "108";
            String tmFc = "202401090600";

            //데이터가 어떤 형식으로 되어있는지 모름
            //한번 더 컴퓨터가 읽을 수 있는 값으로 encode 변환시켜준 것
            String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
            String encodedUrl = String.format("%s?serviceKey=%s&pageNo=%s&numOfRows=%s&dataType=%s&stnId=%s&tmFc=%s",
                    apiUrl, encodedApiKey, numOfRows, pageNo, dataType, stnId, tmFc);

            URL url = new URL(encodedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
//String url = String.format("%s?apiKey=%s&numOfRows=%spageNo=%s&type=%s&dissCd=%s&znCd=%s");


