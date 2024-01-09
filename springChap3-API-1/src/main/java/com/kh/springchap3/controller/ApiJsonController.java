package com.kh.springchap3.controller;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
public class ApiJsonController {

    @GetMapping("/weather/add")
    public String DBInsert() {

		//데이터를 시작하기 전에는 StringBuilder
        StringBuilder result = new StringBuilder();

        try {
            String apiUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
            String apiKey = "ZTckEfSDn30pQA8mkLQt0gcRfTMDnv9Cd685DihsfQ9tnqxwYucJfQ72YCuQ9mkvW7re+ZqXLy/hI8+cqxjUcA==";
            String pageNo = "1";
            String numOfRows = "10";
            String dataType = "xml";
            String stnId = "108";
            String tmFc = "202401090600";
            String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
            String encodedUrl = String.format("%s?serviceKey=%s&pageNo=%s&numOfRows=%s&dataType=%s&stnId=%s&tmFc=%s",
            		apiUrl, encodedApiKey, numOfRows, pageNo, dataType, stnId, tmFc);

            URL url = new URL(encodedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line; //하나씩 값을 넣을 때
            while ((line = reader.readLine()) != null) {
                result.append(line); 
            }
            reader.close();
            connection.disconnect();

            insertIntoOracleDB(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //json 파일로 변환해서 보여주기(xml -> json)
        String jsonResult = convertXmlToJson(result.toString());
        return jsonResult;
        //return result.toString();
    }
    //xml로 보이는 파일을 json 형식으로 변환해서 화면에 출력하는 메서드
    private String convertXmlToJson(String xmlDate) {
    	JSONObject jsonObj = XML.toJSONObject(xmlDate);
    	return jsonObj.toString();
    }

    
    private void insertIntoOracleDB(String data) {
    	        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    	        String username = "user3";
    	        String password = "user3";

    	        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
    	            String sql = "INSERT INTO culture (id, data) VALUES (culture_seq.NEXTVAL, ?)";
    	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    	                statement.setString(1, data);
    	                statement.executeUpdate();
    	            }

    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    }
}