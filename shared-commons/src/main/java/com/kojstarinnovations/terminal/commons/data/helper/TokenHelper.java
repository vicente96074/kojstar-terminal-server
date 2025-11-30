package com.kojstarinnovations.terminal.commons.data.helper;

import java.util.UUID;

/**
 * TokenHelper - Helper class for token generation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class TokenHelper {

    /**
     * Generate a token
     *
     * @return String
     */
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}