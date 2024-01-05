package com.kh.springchap3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springchap3.model.UserSNS;



public interface UserRepository  extends JpaRepository<UserSNS, Long>{

}