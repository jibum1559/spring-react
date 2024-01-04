package com.kh.springchap3.config;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
        		naverClientRegistration(),
        		googleClientRegistration());
    }
    //인증 통합 관리하는 매니저
    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .successHandler(new SimpleUrlAuthenticationSuccessHandler("/loginSuccess")));
        return http.build();
    }
    
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
    
}