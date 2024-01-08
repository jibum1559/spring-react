package com.kh.springchap4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.springchap4.mapper.MemberMapper;
import com.kh.springchap4.model.Member;

@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;
    
    public void signUpMember(Member member) {
        memberMapper.insertMember(member);
    }
    
    public List<Member> getAllMembers() {
    	return memberMapper.getAllMembers();
    }
    
    public void updateMember(Member memberID) {
    	memberMapper.updateMember(memberID);
    }
}