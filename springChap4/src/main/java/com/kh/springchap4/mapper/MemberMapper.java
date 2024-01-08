package com.kh.springchap4.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.springchap4.model.Member;

@Mapper
public interface MemberMapper {
	void insertMember(Member member);

	List<Member> getAllMembers();
	
	void updateMember(Member memberID);
}