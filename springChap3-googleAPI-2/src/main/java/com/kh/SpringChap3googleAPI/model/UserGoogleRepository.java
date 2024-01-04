package com.kh.SpringChap3googleAPI.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGoogleRepository extends JpaRepository<UserGoogle, Long> {
    UserGoogle findByUsername(String username);
}