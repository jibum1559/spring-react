package com.kh.springchap4.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.springchap4.model.Member;
import com.kh.springchap4.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
    private MemberService memberService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("member", new Member());
		return "register";
	}
	
	@PostMapping("/register")
    public String registerMember(Member member) {
        memberService.signUpMember(member);
        return "redirect:/member/register"; 
    }
	
	@GetMapping("/memberList")
	public String getAllMembers(Model model) {
		List<Member> members = memberService.getAllMembers();
		model.addAttribute("members", members);
		return "memberList";	
	}
	
	@GetMapping("/login/oauth2/code/naver")
	public String naverLoginCallback(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
		Object response = oauth2User.getAttribute("response");
		//instanceof : 객체가 인스턴스인지 아닌지를 판단하는 것, 왜?
		//oauth2User 인증에서는 사용자 정보가 map과 유사한 구조로 캡슐화
		//response로 값을 가지고오는데 Map에 인스턴스인지를 확인해보는 것
		//만약에 response가 Map의 구조로 되어있다면 email이나 name의 정보를 추출해서 원하는 정보를 가지고 오겠다는 의미
		if(response instanceof Map) {
			Map<String, Object> responseMap = (Map<String, Object>) response;
			String email = (String) responseMap.get("email");
			String name = (String) responseMap.get("name");
			Member naverMember = new Member();
			naverMember.setEmail(email);
			naverMember.setFullname(name);
			naverMember.setUsername("");
			model.addAttribute("member", naverMember);
			return "register";
		} else {
			return "redirect:/error";
		}
	}
}