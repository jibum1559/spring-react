package com.kh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class MemberController {
	public String memberLogin() {
		//로그인할 때 필요한 코드를 작성해주면 됨
		return "redirect:/";
	}
}
