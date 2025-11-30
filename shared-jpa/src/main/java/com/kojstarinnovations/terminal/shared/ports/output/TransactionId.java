package com.kojstarinnovations.terminal.shared.ports.output;

import java.util.UUID;

/**
 * TransactionId - Class to generate a transaction id
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class TransactionId {

    /**
     * Method to generate a transaction id
     *
     * @return a transaction id
     */
    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}