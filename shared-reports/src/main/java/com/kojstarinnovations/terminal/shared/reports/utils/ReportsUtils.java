package com.kojstarinnovations.terminal.shared.reports.utils;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.Map;

/**
 * ReportsUtils - Class to handle reports in the project
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class ReportsUtils {

    /**
     * Get the bytes of the report
     *
     * @param connection connection to the database
     * @param filename   name of the report file
     * @param parameters parameters of the report
     * @return bytes of the report
     */
    @SneakyThrows
    public static byte[] getBytes(Connection connection, String filename, Map<String, Object> parameters) {
        JasperReport jasperReport = JasperCompileManager.compileReport(Files.getInputStreamReport(filename));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}