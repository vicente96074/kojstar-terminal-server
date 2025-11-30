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

    /**
     * Method to validate the token
     *
     * @param token the token to be validated
     * @return boolean true if the token is valid, false otherwise
     */
    boolean validateToken(String token);

    /**
     * Method to get the username from the token
     *
     * @param token the token to be used
     * @return String username
     */
    String getUsernameFromToken(String token);
}