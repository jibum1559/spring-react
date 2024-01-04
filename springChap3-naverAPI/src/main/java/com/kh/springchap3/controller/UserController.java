package com.kh.springchap3.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

		@GetMapping("/login")
		public String loginPage() {
			return "index";
		}
		
		// 구글 로그인을 위한 URL 추가
		@GetMapping("/oauth2/authorization/google")
		public String googleLogin() {
			return "redirect:/oauth2/authorization/google";
		}
		
		
		@GetMapping("/loginSuccess")
		public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
			System.out.println("OAuth2User Attributes : " + principal.getAttributes().get("response")); 
			
			//네이버는 response 안에 josn으로 묶여있는 부분 참고(그래서 principal 로 로그확인)
			//OAuth2User Attributes : {id=7MHYwKXqdADpCwHR7IexXTeYFxfPw-_qF413MJdj71w, email=jibum1559@naver.com, name=김승범}
			//OAuth2User Attributes : Name: [{id=7MHYwKXqdADpCwHR7IexXTeYFxfPw-_qF413MJdj71w, email=jibum1559@naver.com, name=김승범}], Granted Authorities: [[OAUTH2_USER]], 
			//User Attributes: [{resultcode=00, message=success, response={id=7MHYwKXqdADpCwHR7IexXTeYFxfPw-_qF413MJdj71w, email=jibum1559@naver.com, name=김승범}}]

			model.addAttribute("name", principal.getAttribute("name"));
			model.addAttribute("email", principal.getAttribute("email"));
			
			return "loginSuccess";
		}
}