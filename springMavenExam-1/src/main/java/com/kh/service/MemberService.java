package com.kh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.mapper.MemberMapper;
import com.kh.vo.MemberVO;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	public MemberVO loninMember(String memberId, String memberPwd) {
		return memberMapper.loginMember(memberId, memberPwd);
	}

}
