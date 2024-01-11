package com.kh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.service.MemberService;
import com.kh.vo.MemberVO;

@Controller
@RequestMapping("/login")
public class MemberController {
	
	@Autowired
	private MemberService memberSerivce;
	
	@GetMapping("/")
	public String loginMember(Model model, String memberId, String memberPwd) {
		MemberVO memberLogin = memberSerivce.loninMember(memberId, memberPwd);
		model.addAttribute("memberLogin", memberLogin);
		return "redirect:/";
		
	}
	

}
