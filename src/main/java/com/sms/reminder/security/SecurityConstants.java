package com.sms.reminder.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 172_800_000; // 2 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    public static final String SIGN_UP_URL = "/register";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String MY_ACCOUNT_URL = "/notifications/user/**";
    public static final String NOTIFICATIONS_URL = "/notifications/";
    public static final String NOTIFICATIONS_SEND_URL = "/notifications/send/**";
}
