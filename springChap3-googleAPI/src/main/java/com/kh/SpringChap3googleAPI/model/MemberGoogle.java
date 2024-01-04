package com.kh.SpringChap3googleAPI.model;

import jakarta.persistence.*;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

@Getter
@Setter
@Entity
public class MemberGoogle {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="mg_seq")
	@SequenceGenerator(name="mg_seq", sequenceName="mg_seq", allocationSize=1)
	private Long id; //기본키
	private String username;
	private String email;
	
}

