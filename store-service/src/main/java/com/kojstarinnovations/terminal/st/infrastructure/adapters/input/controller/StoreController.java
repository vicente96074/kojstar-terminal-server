package com.kojstarinnovations.terminal.st.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import com.kojstarinnovations.terminal.st.application.data.request.StoreRequest;
import com.kojstarinnovations.terminal.st.domain.ucextends.StoreUC;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store-service/store")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StoreController {

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess('SUPER_ADMIN', 'CEO')")
    public ResponseEntity<StoreResponse> create(@Valid @RequestBody StoreRequest request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.OK);
    }

    @PostMapping(value = "/create-by-system", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess('SUPER_ADMIN')")
    public ResponseEntity<StoreResponse> createBySuperAdmin(@Valid @RequestBody StoreRequest request) {
        return new ResponseEntity<>(service.execute(request), HttpStatus.OK);
    }

    private final StoreUC service;
}