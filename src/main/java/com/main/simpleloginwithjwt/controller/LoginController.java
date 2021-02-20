package com.main.simpleloginwithjwt.controller;

import com.main.simpleloginwithjwt.request.LoginRequest;
import com.main.simpleloginwithjwt.response.LoginResponse;
import com.main.simpleloginwithjwt.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/auth")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            LoginResponse response = new LoginResponse();
            response.setStatusCode(403);
            response.setSuccess(false);
            response.setMessage("Login Failed! </br> Username Or Password Is Something Wrong!");
            response.setToken(null);
            return response;
        }

        return this.jwtUtils.generateToken(loginRequest.getUsername());
    }
}
