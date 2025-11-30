package com.kojstarinnovations.terminal.commons.data.helper;

import java.util.logging.Logger;

/**
 * DocumentationsHelper - Helper for Documentations, like DPI, NIT, Passport, etc.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class DocumentationsHelper {

    /**
     * Format DPI
     *
     * @param dpi String
     * @return String
     */
    public static String formatDpi(String dpi) {

        if (dpi == null || dpi.isEmpty()) {
            throw new IllegalArgumentException("El DPI no puede ser nulo o vacío");
        }

        // First step: Remove all whitespaces from the DPI
        String formattedDpi = getString(dpi);

        // Fifth step: Validate the formatted DPI
        if (!formattedDpi.matches("\\d{4} \\d{5} \\d{4}")) {
            throw new IllegalArgumentException("El DPI debe tener el formato XXXX XXXXX XXXX");
        }

        // Sixth step: Print the valid DPI
        Logger.getLogger("AssociateHelper").info("DPI válido: " + formattedDpi);

        return formattedDpi;
    }

    /**
     * Get String
     *
     * @param dpi String
     * @return String
     */
    private static String getString(String dpi) {
        String cleanedDpi = dpi.replaceAll("\\s+", "");

        // Second step: Validate that the DPI only contains numbers
        if (!cleanedDpi.matches("\\d+")) {
            throw new IllegalArgumentException("El DPI solo puede contener números");
        }

        // Third step: Validate that the DPI has exactly 13 digits
        if (cleanedDpi.length() != 13) {
            throw new IllegalArgumentException("El DPI debe tener exactamente 13 dígitos");
        }

        // Fourth step: Format the DPI
        return cleanedDpi.replaceAll("(\\d{4})(\\d{5})(\\d{4})", "$1 $2 $3");
    }
}