package com.kojstarinnovations.terminal.commons.data.constants;

public class RedisConstants {
    // Prefijos para las claves de Redis
    public static final String PREFIX_REFRESH_TOKEN = "refresh_token:";
    public static final String PREFIX_SUB_TOKENS = "sub_tokens:";
    public static final String PREFIX_TOKEN_IP = "token_ips:";

    // Bloqueo de ips sospechosos y user-agents
    public static final String PREFIX_SUSPICIOUS_IP = "suspicious_ip:";
    public static final String PREFIX_SUSPICIOUS_USER_AGENT = "suspicious_user_agent:";
    public static final Integer MAX_ATTEMPTS_BY_TWO_FACTOR = 5;

    // Intentos de login fallidos
    public static final String PREFIX_ATTEMPTS_BY_SUB = "attempts_by_sub:";

    // Claves por sms o email
    public static final String PREFIX_SMS = "sms:";
    public static final String PREFIX_EMAIL = "email:";
}