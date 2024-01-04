package com.kh.springchap3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class UserNaver {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="usernaver_seq")
    @SequenceGenerator(name = "usernaver_seq", sequenceName="usernaver_seq",allocationSize=1)
	private Long id;
	
	private String username;
	private String email;
}
