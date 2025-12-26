package com.kojstarinnovations.terminal.commons.data.helper;

import java.util.UUID;

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
}
