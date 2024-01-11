package com.kh.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.vo.MemberVO;

//MemberMapper -> MemberDAO
@Mapper
public interface MemberDAO { //MemberMapper와 같음
	MemberVO loginMember(String memberId, String memberPwd);
}
