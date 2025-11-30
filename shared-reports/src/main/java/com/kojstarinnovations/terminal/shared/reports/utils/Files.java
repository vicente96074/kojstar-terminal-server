package com.kojstarinnovations.terminal.shared.reports.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Files - Class to handle files in the project,
 * such as reports and images
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class Files {

    /**
     * Get the input stream of the report
     *
     * @param filename name of the report
     * @return input stream of the report
     * @throws FileNotFoundException if the report is not found
     */
    private InputStream inputStreamReport(String filename) throws FileNotFoundException {
        InputStream jrxmlInputStream = getClass().getClassLoader().getResourceAsStream("reports/" + filename);

        if (jrxmlInputStream == null) {
            throw new FileNotFoundException("El archivo jrxml no se encontró: " + filename);
        }

        return jrxmlInputStream;
    }

    /**
     * Get the input stream of the report
     *
     * @param filename name of the report
     * @return input stream of the report
     * @throws FileNotFoundException if the report is not found
     */
    public static InputStream getInputStreamReport(String filename) throws FileNotFoundException {
        return getInstance().inputStreamReport(filename);
    }

    /**
     * Singleton
     */
    private Files() {

    }

    public static Files getInstance() {
        if (instance == null) {
            instance = new Files();
        }
        return instance;
    }

    private static Files instance;
}