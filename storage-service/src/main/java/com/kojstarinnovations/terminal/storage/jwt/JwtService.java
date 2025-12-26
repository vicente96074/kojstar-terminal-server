package com.kojstarinnovations.terminal.storage.jwt;


import org.springframework.security.core.Authentication;

/**
 * JwtService - Interface to provide the methods to validate a token and get the authentication from it
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface JwtService {

    /**
     * validateToken method
     *
     * @param token the token to be validated
     * @return a boolean value to indicate if the token is valid or not
     */
    boolean validateToken(String token);

    /**
     * getAuthentication method
     *
     * @param token the token to get the authentication from
     * @return the authentication object
     */
    Authentication getAuthentication(String token);
}