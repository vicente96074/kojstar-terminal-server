package com.kojstarinnovations.terminal.storage.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.commons.data.transport.commons.GetFileRequest;
import com.kojstarinnovations.terminal.commons.data.payload.commons.StorageResponse;
import com.kojstarinnovations.terminal.storage.domain.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

/**
 * StorageController - Controller class for storage service
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/storage-service")
@RequiredArgsConstructor
public class StorageController {

    @PostMapping("/upload-identification")
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<StorageResponse> uploadFileIdentification(
            @RequestParam("file") MultipartFile file,
            @RequestParam("identificationType") String identificationType
    ) throws IOException {
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(storageService.storeIdentification(file, identificationType, host), HttpStatus.OK);
    }

    @PostMapping("/upload-commodity-category")
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<StorageResponse> uploadFileCommodityCategory(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(storageService.storeCommodityCategory(file, host), HttpStatus.OK);
    }

    @PostMapping("/upload-commodity")
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<StorageResponse> uploadFileCommodity(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(storageService.storeCommodity(file, host), HttpStatus.OK);
    }

    @PostMapping("/upload-brand")
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<StorageResponse> uploadFileBrand(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(storageService.storeBrand(file, host), HttpStatus.OK);
    }

    @PostMapping("/upload-store")
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<StorageResponse> uploadFileStore(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(storageService.storageStore(file, host), HttpStatus.OK);
    }

    @PostMapping("/upload-store-branch")
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<StorageResponse> uploadFileStoreBranch(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(storageService.storageStoreBranch(file, host), HttpStatus.OK);
    }

    @PostMapping("/get-by-filename")
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<Resource> getFile(@RequestBody GetFileRequest request) throws IOException {
        Resource file = storageService.loadAsResource(request);

        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/get-assets-img")
    public ResponseEntity<Resource> getAssetsImg(@RequestParam("filename") String filename) throws IOException {
        Resource file = storageService.loadAssetsImg(filename);

        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    private final StorageService storageService;
    private final HttpServletRequest request;
}

