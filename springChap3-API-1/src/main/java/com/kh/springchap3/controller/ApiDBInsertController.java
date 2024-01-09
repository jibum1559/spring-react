package com.kh.springchap3.controller;
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
public class ApiDBInsertController {

    @GetMapping("/map_info/add")
    public String DBInsert() {

		//데이터를 시작하기 전에는 StringBuilder
        StringBuilder result = new StringBuilder();

        try {
            String apiUrl = "http://apis.data.go.kr/B550928/dissForecastInfoSvc/getDissForecastInfo";
            String apiKey = "65liqYkK/8aC6Rh048XPwFgemJxH+MbwP3xXZX99CvFgz0Fu4q+dhC5NIQlLfrLVjpnKo8xX6yALLyPhICI0HQ==";
            String numOfRows = "10";
            String pageNo = "1";
            String responseType = "xml";
            String dissCd = "1";
            String znCd = "11";
            String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
            String encodedUrl = String.format("%s?serviceKey=%s&numOfRows=%s&pageNo=%s&responseType=%s&dissCd=%s&znCd=%s",
                    apiUrl, encodedApiKey, numOfRows, pageNo, responseType, dissCd, znCd);

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

            insertIntoOracleDB(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
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