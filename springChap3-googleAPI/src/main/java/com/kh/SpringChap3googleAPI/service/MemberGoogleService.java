package com.kh.SpringChap3googleAPI.service;

import java.util.Optional;

import com.kh.SpringChap3googleAPI.model.MemberGoogle;

public interface MemberGoogleService {
	MemberGoogle findByUsername(String username);
	void saveMember(MemberGoogle user);
}
