package com.kh.SpringChap3googleAPI.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
    private final UserGoogleRepository userGoogleRepository;

    @Autowired
    public UserServiceImpl(UserGoogleRepository userGoogleRepository) {
        this.userGoogleRepository = userGoogleRepository;
    }

    @Override
    public UserGoogle findByUsername(String username) {
        return userGoogleRepository.findByUsername(username);
    }

    @Override
    public void saveUser(UserGoogle user) {
        userGoogleRepository.save(user);
    }
}