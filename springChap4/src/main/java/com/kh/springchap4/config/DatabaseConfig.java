package com.kh.springchap4.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfig {
	
	/*
	DataSourceInitializer 객체는 DB 초기화를 수행할 수 있는
	데이터 소스와 populator 설정해주는 것이 작성되어있음
	ResourceDatabasePopulator: DB를 초기화 하는데 사용
	addScript DB를 초기화 하고 추가할 스크립트 파일을 추가 
	sql/create-members-table.sql에 추가할 DDL문을 작성해서 넣어줌
	*/

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator(dataSource));
		return initializer;
	}
	
	private ResourceDatabasePopulator databasePopulator(DataSource dataSource) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		
		if(!tableExists(dataSource, "members")) {
			populator.addScript(new ClassPathResource("sql/create-members-table.sql"));
		}
		return populator;
	}
	
	private boolean tableExists(DataSource dataSource, String tableName) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = "SELECT COUNT(*) FROM ALL_TABLES WHERE TABLE_NAME = UPPER(?)";
		int count = jdbcTemplate.queryForObject(query, Integer.class, tableName.toUpperCase());
		return count > 0;
	}
}