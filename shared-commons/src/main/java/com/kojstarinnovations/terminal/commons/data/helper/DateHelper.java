package com.kojstarinnovations.terminal.commons.data.helper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * DateHelper - Helper class for Date manipulation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class DateHelper {

    /**
     * Add months to a localdate and return the new date
     *
     * @param date   the date to add months to
     * @param months the number of months to add
     * @return the new date
     */
    public static LocalDate addMonths(LocalDate date, int months) {
        return date.plusMonths(months);
    }

    /**
     * Add months to a localdatetime and return the new date
     *
     * @param date   the date to add months to
     * @param months the number of months to add
     * @return the new date
     */
    public static LocalDateTime addMonths(LocalDateTime date, int months) {
        return date.plusMonths(months);
    }

    /**
     * Calculate the days between two localdates
     *
     * @param from  the first date
     * @param until the second date
     * @return the number of days between the two dates
     */
    public static int daysBetweenDates(LocalDate from, LocalDate until) {
        try {
            return Math.toIntExact(ChronoUnit.DAYS.between(from, until));
        } catch (ArithmeticException | DateTimeException e) {
            return 0;
        }
    }

    /**
     * Calculate the days between two localdatetimes
     *
     * @param from  the first date
     * @param until the second date
     * @return the number of days between the two dates
     */
    public static int daysBetweenDates(LocalDateTime from, LocalDateTime until) {
        try {
            return Math.toIntExact(ChronoUnit.DAYS.between(from, until));
        } catch (ArithmeticException | DateTimeException e) {
            return 0;
        }
    }

    /**
     * Calculate the days of a month from a localdate
     *
     * @param date the date to calculate the days of the month
     * @return the number of days in the month
     */
    public static int daysOfMonth(LocalDate date) {
        boolean leapYear = false;

        int year = date.getYear();
        int month = date.getMonthValue();
        leapYear = year % 4 == 0;

        if (month == 2 && leapYear) { //Leap year february will have 29 days
            return 29;
        } else if (month == 2) { // February will have 28 days
            return 28;
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) { // Months with 31 days
            return 31;
        } else { // Months with 30 days
            return 30;
        }
    }

    /**
     * Calculate the days of a month from a localdatetime
     *
     * @param date the date to calculate the days of the month
     * @return the number of days in the month
     */
    public static int daysOfMonth(LocalDateTime date) {
        boolean leapYear = false;

        int year = date.getYear();
        int month = date.getMonthValue();
        leapYear = year % 4 == 0;

        if (month == 2 && leapYear) { //Leap year february will have 29 days
            return 29;
        } else if (month == 2) { // February will have 28 days
            return 28;
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) { // Months with 31 days
            return 31;
        } else { // Months with 30 days
            return 30;
        }
    }

    /**
     * Insert a day into a localdate
     *
     * @param l   the localdate to insert the day
     * @param dia the day to insert
     * @return the new localdate
     */
    public static LocalDate insertDay(LocalDate l, int dia) {
        return l.withDayOfMonth(dia);
    }

    /**
     * Insert a day into a localdatetime
     *
     * @param l   the localdatetime to insert the day
     * @param dia the day to insert
     * @return the new localdatetime
     */
    public static LocalDateTime insertDay(LocalDateTime l, int dia) {
        return l.withDayOfMonth(dia);
    }

    /**
     * Convert a string to a localdate
     *
     * @param date the string to convert
     * @return the localdate
     */
    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }

    /**
     * Calculate the months between two localdates
     *
     * @param from  the first date
     * @param until the second date
     * @return the number of months between the two dates
     */
    public static long monthsBetween(LocalDate from, LocalDate until) {
        long months = ChronoUnit.MONTHS.between(from, until);

        if (until.getDayOfMonth() == until.getMonth().length(until.isLeapYear()) && from.getDayOfMonth() > 28) {
            months++;
        }

        return months;
    }

    /**
     * Calculate the months between two localdatetimes
     *
     * @param from  the first date
     * @param until the second date
     * @return the number of months between the two dates
     */
    public static long monthsBetween(LocalDateTime from, LocalDateTime until) {
        long months = ChronoUnit.MONTHS.between(from, until);

        if (until.getDayOfMonth() == until.getMonth().length(until.toLocalDate().isLeapYear()) && from.getDayOfMonth() > 28) {
            months++;
        }

        return months;
    }

    /**
     * Check if a localdate is less or equal to a range of days
     *
     * @param dateFrom the date to check
     * @param dateTo   the date to compare
     * @param range    the range of days
     * @return true if the date is less or equal to the range
     */
    public static boolean lessOrEqualToRange(LocalDate dateFrom, LocalDate dateTo, int range) {
        return !dateTo.isAfter(dateFrom.plusDays(range)); //If dateTo is not after dateFrom + range days, then it is less or equal to range
    }

    // Date Time Formatter
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}