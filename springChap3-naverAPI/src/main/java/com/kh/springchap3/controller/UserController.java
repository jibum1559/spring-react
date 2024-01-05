package com.kh.springchap3.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap3.repository.UserRepository;


//@Controller
/*
@CrossOrigin : 
-각 컨트롤러마다 바라보는 url 이 다를 수 있기 때문에 어떤 도메인을 허용해줄지 작성해주는 공간 

origins :
-하나의 도메인이 아니라 다수의 도메인을 넣을 수 있음 
-작성해준 도메인의 요청을 허용해주는 것

allowCredentials : 인증된 사용자의 쿠키를 요청에 포함할 수 있도록 허용할지에 대한 여부

allowedHeaders : 허용할 수 있는 헤더를 지정, * 표시는 모든 헤더 허용
("X-Requested-With", "Origins", "Content-type", "Accept", "Authorization")
methods : 원하는 메서드만 설정해서 받을 수 있도록 한 번 더 처리할 수 있음
{RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} 기본 값들
 */
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true", allowedHeaders="*")
@RestController
public class UserController {
	@Autowired
	private final UserRepository userRepository;
	

	public UserController (UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "index";
	}

	// 네이버 로그인을 위한 URL 추가
	@GetMapping("/oauth2/authorization/naver")
	public String googleLogin() {
		return "redirect:/oauth2/authorization/naver";
	}
	
	@GetMapping("/loginSuccess")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
		model.addAttribute("name", principal.getAttribute("name"));
		model.addAttribute("email", principal.getAttribute("email"));
		return "loginSuccess";
		
		
		/*
		@GetMapping("/loginSuccess")
		public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, 
									@RequestParam(value="naverResponse", required = false) String naverResponse, 
									Model model) {
			
			System.out.println("OAuth2User Attributes : " + principal.getAttributes().get("response")); 
			//네이버는 response 안에 josn으로 묶여있는 부분 참고(그래서 principal 로 로그확인)
			//OAuth2User Attributes : {id=7MHYwKXqdADpCwHR7IexXTeYFxfPw-_qF413MJdj71w, email=jibum1559@naver.com, name=김승범}
			//OAuth2User Attributes : Name: [{id=7MHYwKXqdADpCwHR7IexXTeYFxfPw-_qF413MJdj71w, email=jibum1559@naver.com, name=김승범}], Granted Authorities: [[OAUTH2_USER]], 
			//User Attributes: [{resultcode=00, message=success, response={id=7MHYwKXqdADpCwHR7IexXTeYFxfPw-_qF413MJdj71w, email=jibum1559@naver.com, name=김승범}}]

			userService.naverLoginService(principal, naverResponse, model);
			
			return "loginSuccess";
			*/
		}
}