package com.kh.SpringChap3googleAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.SpringChap3googleAPI.model.MemberGoogle;

public interface MemberDetailRepository extends JpaRepository<MemberGoogle, Long>{
	Optional<MemberGoogle> findByUsername(String username);

}
