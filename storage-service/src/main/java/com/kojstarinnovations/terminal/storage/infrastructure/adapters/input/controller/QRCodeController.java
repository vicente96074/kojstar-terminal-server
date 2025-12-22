package com.kojstarinnovations.terminal.storage.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.storage.domain.service.QRCodeGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * QRCodeController - Controller class for QR code generation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/storage-service/qrcode")
public class QRCodeController {

    /**
     * Generate QR code image
     *
     * @param content content to be encoded in the QR code
     * @param width   width of the QR code image
     * @param height  height of the QR code image
     * @return byte array of the QR code image
     */
    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    @PreAuthorize("@securityService.hasRole('SUPER_ADMIN') or @securityService.hasRole('CEO') or @securityService.hasRole('REGIONAL_MANAGER')")
    @SneakyThrows
    public byte[] generateQRCode(@RequestParam("content") String content, @RequestParam("width") int width, @RequestParam("height") int height) {
        return service.generateQrCodeImage(content, width, height);
    }

    private final QRCodeGeneratorService service;
}