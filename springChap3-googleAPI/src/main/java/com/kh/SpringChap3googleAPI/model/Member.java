package com.kh.SpringChap3googleAPI.model;

import jakarta.persistence.*;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

@Getter
@Setter
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="member_seq")
	@SequenceGenerator(name="member_seq", sequenceName="member_seq", allocationSize=1)
	private Long id; //기본키
	private String email;
	private String name;
	
	public Member() {
		
	}
	public Member(String email, String name) {
		this.email = email;
		this.name = name;
	}
}
