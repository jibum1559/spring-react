package com.kh.SpringChap3googleAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.SpringChap3googleAPI.model.MemberGoogle;
import com.kh.SpringChap3googleAPI.service.MemberGoogleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/oauth")
public class OAuthController {
	@Autowired
	private MemberGoogleService memberGoogleService;
	
	@GetMapping("/loginSuccess")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
		String email = oauthUser.getAttribute("email");
		MemberGoogle user = memberGoogleService.findByUsername(email);
		System.out.println("OAuth2User: " + oauthUser);
		System.out.println("이메일 속성: " + email);
		//service
		if(user ==null) {
			user = new MemberGoogle();
			user.setUsername(email);
			user.setEmail(email);
			memberGoogleService.saveMember(user);
			
			model.addAttribute("newUser", true);
		}
		
		return "loginSuccess";
	}
	@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
