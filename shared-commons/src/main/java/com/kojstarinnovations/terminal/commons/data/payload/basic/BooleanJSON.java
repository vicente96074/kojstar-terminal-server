package com.kojstarinnovations.terminal.commons.data.payload.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BooleanJSON - Payload for Boolean JSON
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BooleanJSON {

    /**
     * Set exists boolean only
     *
     * @param exists boolean
     * @return BooleanJSON
     */
    public static BooleanJSON ofExists(boolean exists) {
        return new BooleanJSON(exists);
    }

    private BooleanJSON(boolean exists) {
        this.exists = exists;
    }

    private boolean exists;
    private boolean success;
    private boolean failure;
    private boolean error;
}