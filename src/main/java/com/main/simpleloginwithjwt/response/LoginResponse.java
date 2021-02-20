package com.main.simpleloginwithjwt.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private int statusCode;
    private boolean success;
    private String message;
    private String token;
}
