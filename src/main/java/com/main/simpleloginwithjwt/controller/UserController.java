package com.main.simpleloginwithjwt.controller;

import com.main.simpleloginwithjwt.entity.User;
import com.main.simpleloginwithjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public User findById(@PathVariable int id) {
        return this.userService.findById(id);
    }
}
