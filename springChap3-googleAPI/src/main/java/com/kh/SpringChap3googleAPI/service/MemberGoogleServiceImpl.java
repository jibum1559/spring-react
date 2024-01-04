package com.kh.SpringChap3googleAPI.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.SpringChap3googleAPI.model.MemberGoogle;
import com.kh.SpringChap3googleAPI.repository.MemberGoogleRepository;

@Service
public class MemberGoogleServiceImpl implements MemberGoogleService {
	private final MemberGoogleRepository memberGoogleRepository;
	
	@Autowired
	public MemberGoogleServiceImpl(MemberGoogleRepository memberGoogleRepository) {
		this.memberGoogleRepository = memberGoogleRepository;
}
	@Override
	public MemberGoogle findByUsername(String username) {
		return memberGoogleRepository.findByUsername(username);
	}
	
	@Override
	public void saveMember(MemberGoogle user) {
		memberGoogleRepository.save(user);
	}
}