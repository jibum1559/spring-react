package com.kh.SpringChap3googleAPI.model;

public interface UserService {
	 UserGoogle findByUsername(String username);
	    void saveUser(UserGoogle user);
}