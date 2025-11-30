package com.kojstarinnovations.terminal.us.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.us.domain.service.ContactUSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service/contact")
public class ContactUSController {

    @GetMapping(value = "/get-phones-by-auth-and-active", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'PROFILE'})")
    public ResponseEntity<List<ContactUSResponse>> getAllActivePhonesByAuth() {
        return ResponseEntity.ok(service.getAllActivePhonesByAuth());
    }

    private final ContactUSService service;
}