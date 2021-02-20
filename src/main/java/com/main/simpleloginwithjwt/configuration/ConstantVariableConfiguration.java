package com.main.simpleloginwithjwt.configuration;

public class ConstantVariableConfiguration {
    public static final String HEADER_AUTH = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET_KEY_FOR_GENERATE_TOKEN = "secretKeyForGenerateToken";
    public static final long EXPIRATION_TOKEN = 7_200_000; // 7.200.00 ms == 2 h
}
