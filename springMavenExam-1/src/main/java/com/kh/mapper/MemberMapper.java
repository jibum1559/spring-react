package com.kh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.vo.MemberVO;

@Mapper
public interface MemberMapper {
	public MemberVO loginMember(String memberId, String memberPwd);
}











