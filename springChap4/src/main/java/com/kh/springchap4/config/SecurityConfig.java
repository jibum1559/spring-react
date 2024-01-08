package com.kh.springchap4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean // 객체의 생성, 관리, 주입(새로 넣어줌)을 담당
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
			.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			
			// 로그인을 하고난 후 로그인 세션을 유지하기 위한 설정
			.formLogin((formLogin) -> formLogin
					.loginPage("/member/login")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/"))
			
			
			// 로그아웃 하고난 후 로그인 세션을 끝내기위한 설정
			.logout((logout) -> logout
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true))
			;
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManage(AuthenticationConfiguration a) throws Exception{
		return a.getAuthenticationManager();
	}
	
	// 비밀번호 변경시 어떤 식으로 암호 처리를 할 지 설정
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(); // BCryptPasswordEncoder() 형식으로 저장
	}
	
}