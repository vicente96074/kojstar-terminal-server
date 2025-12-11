package com.kojstarinnovations.terminal.auths.jwt;


import org.springframework.security.core.Authentication;

/**
 * JwtService - This interface is responsible for generating and validating JWT tokens
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface JwtService {

    /**
     * Method to generate the access token
     *
     * @param authentication Authentication object with user data
     * @return String token
     */
    String generateAccessToken(Authentication authentication);
}