package com.kh.SpringChap3googleAPI.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.SpringChap3googleAPI.model.MemberGoogle;
import com.kh.SpringChap3googleAPI.repository.MemberDetailRepository;


@Service
public class MemberDetailServiceImpl implements UserDetailsService {
	@Autowired
	private MemberDetailRepository memberDetailRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberGoogle user = memberDetailRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("일치하는 유저 정보를 찾을 수 없습니다." + username));
		return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                "", // "{noop}", Oauth 인증에서는 패스워드를 사용하지 않습니다.
                Collections.emptyList());
				
	}
	

}
