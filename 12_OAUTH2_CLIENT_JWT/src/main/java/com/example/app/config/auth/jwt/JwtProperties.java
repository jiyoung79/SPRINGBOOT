package com.example.app.config.auth.jwt;

/**
 * JWT 기본 설정값
 */
public class JwtProperties {
    public static final int EXPIRATION_TIME = 600000; // 쿠키유효시간 : 10분
    public static final String COOKIE_NAME = "JWT-AUTHENTICATION";
}