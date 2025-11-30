package com.kojstarinnovations.terminal.commons.data.payload.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomBarcodeResponse - Payload for Custom Barcode
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomBarcodeResponse {
    private String barcode;
}