package com.kh.SpringChap3googleAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.SpringChap3googleAPI.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	//추가적으로 필요한 메서드 작성

}
