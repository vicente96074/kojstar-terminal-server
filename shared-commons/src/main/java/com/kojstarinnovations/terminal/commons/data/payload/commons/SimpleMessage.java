package com.kojstarinnovations.terminal.commons.data.payload.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SimpleMessage - Payload for Simple Message
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SimpleMessage {
    public static SimpleMessage of(String message) {
        return new SimpleMessage(message);
    }
    private String message;
}