package com.kh.springchap3.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiUrlEncoderController {
	
	@GetMapping("/allow_info/basic")
    public String allowBasic() {
        StringBuffer result = new StringBuffer();
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst"); /*URL*/
            urlBuilder.append("?ServiceKey=ZTckEfSDn30pQA8mkLQt0gcRfTMDnv9Cd685DihsfQ9tnqxwYucJfQ72YCuQ9mkvW7re%2BZqXLy%2FhI8%2BcqxjUcA%3D%3D"); /*Service Key*/
            urlBuilder.append("&pageNo=1"); /*페이지 번호*/
            urlBuilder.append("&numOfRows=10"); /*한 페이지 결과수*/
            urlBuilder.append("&dataType=json"); /*결과 json 포맷*/
            urlBuilder.append("&stnId=108"); 
            urlBuilder.append("&tmFc=202401090600"); 
            
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line).append("\n");
            }
            rd.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}

//결과로 보여줄 포맷 만약 xml로 보여주고싶다면 xml로 설정