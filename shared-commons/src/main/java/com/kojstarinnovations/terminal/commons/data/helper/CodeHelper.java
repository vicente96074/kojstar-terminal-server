package com.kojstarinnovations.terminal.commons.data.helper;

/**
 * CodeHelper - Helper for Code Generation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class CodeHelper {

    /**
     * Generate Code
     *
     * @return String
     */
    public static String generateCode() {
        //Like 840782
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}
