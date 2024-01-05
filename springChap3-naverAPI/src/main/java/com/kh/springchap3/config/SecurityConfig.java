package com.kh.springchap3.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	//CORS(Cross-Origin Resource Sharing
	//Cors는 웹 브라우저에서 실행되는 자바스크립트가 다른 도메인에 접근할 수 있도록 해주는 보안 기술
	//주로 코딩에서나(웹에서나) API서버에서 다른 도메인으로부터 HTTP 요청을 허용하도록 설정하는 데 사용
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		//CorsConfigurationSource 에 대한 객체 생성
		CorsConfiguration configuration = new CorsConfiguration();
		//허용할 오리진(주소) 목록 설정 (여기서는 http://localhost:3000 만 허용해준 것
		//만약에 주소가 2가지 이상이면, 사용해서 주소를 추가할 수 있음
		//예를 들어 corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001")); 
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); 
		
		//허용할 HTTP 메서드 목록 설정
		/*
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS, "PATCH"));
		"GET" : 데이터 조회하기 위한 용도로 사용 
		"POST" : 서버에 데이터를 제출하기 위한 용도 
		"DELETE" : 서버에서 데이터(리소스)를 삭제하기 위한 용도 
		"PUT" : 서버에서 데이터를 업데이트 하기 위한 용도로 클라이언트가 수정한 내용을 데이터베이스에 업데이트 하기 위해 사용 
		"OPTIONS : 실제로 요청하기 전에 브라우저가 서버한테 해당 데이터에 대해 어떤 메서드와 헤더들을 사용할 수 있는지 확인하기 위한 용도
				   CORS에서 사전 검사를 요청하는 데 많이 사용
		"PATCH" : 데이터에 일부만 업데이트하기 위한 용도(중간 중간 하나씩만 업데이트하기 위해서)
				  PUT과 비슷하지만 전체 데이터를 업데이트 하는 대신 일부만 업데이트 할 때 사용
				  최근에는 PC 사양이 좋아져서 PUT만 사용해도 문제는 없지만 게임 같은 환경에서는 사용함
		*/
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
		//HTTP 에서 허용할 헤더 목록 설정
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origins", "Content-type", "Accept", "Authorization"));
		//자격 증명 (자격은 cookie, 헤더 등)을 허용할지 여부 결정
		//주로 로그인 상태를 유지하거나 사용자 관련 정보를 요청과 함께 전송할 때 많이 사용
		configuration.setAllowCredentials(true);
		
		//org.springframework.web.cors.CorsConfigurationSource;
		//UrlBasedCorsConfigurationSource 객체 생성 등록
		//경로별로 다른 CORS 구성을 관리할 수 있도록 도와줌
		//경로를 설정해서 설정한 경로에만 Cors 설정을 동일하게 적용하겠다는 의미
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		//CORS 구성이 적용되도록 설정된 source를 반환
		return source;
	}	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		//활성화
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		//비활성화
		//.cors(cors -> cors.disable())
		/*
		 만약에 CSRF을 사용하게 되면 SNS(Google, Kakao, Naver 등 에서 로그인을 위한 토큰)와 CSRF 에서 발급해주는 토큰이 존재함(한 번 더 인증해주는,,)
		CSRF(Cross-Site Request Fogery) 공격을 방지하기 위한 방법 중 하나
		CSRF 토근이 있으며, 
		-이 토큰은 사용자의 세션과 관련된 고유한 값으로 각 요청에 포함되어있는지 토큰을 통해 확인하고 유효한지를 검증
		-사용자가 로그인할 때마다 서버는 특별한 1회서 CSRF 토큰을 생성하고 이 토큰을 안전하게 쿠키에 저장
		-웹 페이지의 폼이나 Ajax 요청에서 토큰을 포함시켜야함
		-보통은 input hidden 필더나 헤더에 토큰을 넣어서 전송
		-1회성이기 때문에 데이터베이스에는 저장하지 않음
		-토큰이 일치하지 않으면 해당 요청은 거부되거나 무시됨
		//.csrf(csrf -> csrf.disable())
		 
		*/
		.authorizeHttpRequests(authorizeHttpRequests ->
		authorizeHttpRequests
		.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		.oauth2Login(oauth2Login ->
		oauth2Login
		.successHandler(new SimpleUrlAuthenticationSuccessHandler("/loginSuccess")));
		return http.build();
	}
	
	//추후 네이버, 카카오, 구글 등록한 정보를 저장
	//InMemoryClientRegistrationRepository 등록하기 위한 공간
	//naverClientRegistration 
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
        		googleClientRegistration(),
        		kakaoClientRegistration(),
        		naverClientRegistration());
    }
    //인증 통합 관리하는 매니저
    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
    	//클라이언트 인증 처리
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .build();
        // 클라이언트 등록 정보와 인증된 클라이언트 저장소를 설정
        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    //위에거랑 같은건가..?
    //인증 통합 관리하는 매니저
    @Bean
    public OAuth2AuthorizedClientManager oauth2AuthorizedClientManager (
    		ClientRegistrationRepository clientRegistrationRepository,
    		OAuth2AuthorizedClientRepository oauth2AuthorizedClientRepository) {
    	//클라이언
    	OAuth2AuthorizedClientProvider oauth2AuthorizedClientProvider =
    			OAuth2AuthorizedClientProviderBuilder.builder()
    			.authorizationCode()
    			.build();
    	DefaultOAuth2AuthorizedClientManager authorizedClientManager = 
    			new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oauth2AuthorizedClientRepository);
    	authorizedClientManager.setAuthorizedClientProvider(oauth2AuthorizedClientProvider);
    	return authorizedClientManager;
    }
   
    //추후 네이버 등록한 정보를 저장
    //네이버 클라이언트의 등록 정보를 생성하는 메서드
    //클라이언트 아이디와 시크릿, 인증 후 리다이렉트 URI 설정
    public ClientRegistration naverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId("8aA9mcsqN8544S_2bNZN")
                .clientSecret("9s0zu30EW_")
                //네이버에서 인증하고나서 OAuth2 엔드포인트 설정
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .clientName("Naver")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
    }
    
    //구글 클라이언트의 등록 정보를 생성하는 메서드
    //클라이언트 아이디와 시크릿, 인증 후 리다이렉트 URI 설정
    public ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("669544904652-boavvtjin3nqm73m4vqrpt7k5lmsioc0.apps.googleusercontent.com")
                .clientSecret("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")
                //네이버에서 인증하고나서 OAuth2 엔드포인트 설정
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .clientName("Google")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com.oauth2/v3/userinfo")
                .userNameAttributeName("sub")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("openid","profile","email")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .build();
    }
    
    //카카오 클라이언트의 등록 정보를 생성하는 메서드
    //클라이언트 아이디와 시크릿, 인증 후 리다이렉트 URI 설정
    public ClientRegistration kakaoClientRegistration() {
    	return ClientRegistration.withRegistrationId("kakao")
    			.clientId("19daeed248d956251afea0c56356d85c")
    			.clientSecret("4ZZdSAGfT52927MndOVOzo3PqWBXE8Pg")
  
    			.redirectUri("http://localhost:8080/login/oauth2/code/kakao")
                .clientName("Kakao")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname") //추후 변경할 동의 내역 설정
                .build();
    }
}