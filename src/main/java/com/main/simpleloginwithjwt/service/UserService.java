package com.main.simpleloginwithjwt.service;

import com.main.simpleloginwithjwt.entity.User;
import com.main.simpleloginwithjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(int id) {
        return this.userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
