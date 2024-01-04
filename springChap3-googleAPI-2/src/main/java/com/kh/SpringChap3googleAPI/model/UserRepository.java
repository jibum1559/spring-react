package com.kh.SpringChap3googleAPI.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserGoogle, Long> {
    Optional<UserGoogle> findByUsername(String username);
}