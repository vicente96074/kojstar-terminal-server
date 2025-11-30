package com.kojstarinnovations.terminal.commons.data.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * DataHelper - Helper for Data Manipulation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class DataHelper {

    /**
     * Convert Integer to BigDecimal
     *
     * @param value Integer
     * @return BigDecimal value
     */
    public static BigDecimal intToBigDecimal(int value) {
        return BigDecimal.valueOf(value);
    }

    /**
     * Round BigDecimal to Scale Value with Half Up Rounding Mode (0.5)
     *
     * @param value BigDecimal
     * @param scale int - Scale Value
     * @return BigDecimal value
     */
    public static BigDecimal bigDecimalScale(Object value, int scale) {
        try {
            BigDecimal decimal = new BigDecimal(value.toString());
            return decimal.setScale(scale, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}