package com.kojstarinnovations.terminal.us.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.commons.data.payload.commons.SimpleMessage;
import com.kojstarinnovations.terminal.commons.data.payload.commons.UserAuthenticated;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.commons.PaginationRequest;
import com.kojstarinnovations.terminal.us.application.data.request.UserRequest;
import com.kojstarinnovations.terminal.us.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.PagedModel;

/**
 * UserController - User Controller, responsible for handling user requests
 * The exception handling is done by the ControllerAdvisor
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service/user")
@CrossOrigin(origins = "*")
public class UserController {

    /**
     * register method
     *
     * @param userRequest The new user to be registered
     * @return The response entity
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER')")
    public ResponseEntity<SimpleMessage> newUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = service.saveWithBasicRolesAndAccesses(userRequest);
        return new ResponseEntity<>(SimpleMessage.of("User registered: " + userResponse.getUsername()), HttpStatus.CREATED);
    }

    /**
     * Get page user active method
     *
     * @param request The request
     * @return The response entity
     */
    @PostMapping(value = "/get-page-user-active", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyRole({'SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER', 'GOOGLE_USER', 'GITHUB_USER'})")
    public ResponseEntity<PagedModel<EntityModel<UserResponse>>> getPageUserActive(@RequestBody PaginationRequest request, PagedResourcesAssembler<UserResponse> assembler) {
        return ResponseEntity.ok(assembler.toModel(service.getActiveStatusPage(
                PageRequest.of(
                        request.getPage(),
                        request.getSize(),
                        Sort.by(
                                request.getAsc() ? Sort.Direction.ASC : Sort.Direction.DESC,
                                request.getOrders()
                        )
                )
        )));
    }

    @GetMapping(value = "/get-by-authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyRole('SUPER_ADMIN', 'CEO', 'REGIONAL_MANAGER', 'GOOGLE_USER', 'GITHUB_USER')")
    public ResponseEntity<UserAuthenticated> getByAuthentication() {
        log.info("Getting user by authentication");
        return new ResponseEntity<>(service.getByAuthentication(), HttpStatus.OK);
    }

    private final UserService service;
}