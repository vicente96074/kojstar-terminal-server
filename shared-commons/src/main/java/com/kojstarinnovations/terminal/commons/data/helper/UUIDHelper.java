package com.kojstarinnovations.terminal.commons.data.helper;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * UUIDHelper - Helper class for UUID generation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class UUIDHelper {

    /**
     * Generate a UUID
     *
     * @return String
     */
    public static String generateUUID(String prefix, Integer length) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if (prefix != null && !prefix.isEmpty()) {
            uuid = prefix + uuid;
        }
        if (length != null && length > 0 && length < uuid.length()) {
            uuid = uuid.substring(0, length);
        }
        return uuid;
    }

    /**
     * Generate a UUID with a prefix null
     *
     * @param length the length of the UUID
     * @return String
     */
    public static String generateUppercaseUUID(Integer length) {
        String uuid = generateUUID(null, length);
        return uuid.toUpperCase();
    }

    /**
     * Generate a random number
     *
     * @param length the length of the number
     * @return Integer
     */
    public static int   generateRandomNumber(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }

        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length);

        // Para length=6: min=100000, max=1000000
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
