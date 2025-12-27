package com.kojstarinnovations.terminal.us.util;

import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.RolRequest;
import com.kojstarinnovations.terminal.us.domain.service.RolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * To create roles in the database when the application starts up
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateRoles {

    /**
     * Method to create the roles
     */
    public void run() {

        List<RolName> rolNames = Stream.of(RolName.values()).toList();

        rolNames.forEach(rolName -> {
            try {
                rolService.save(
                        RolRequest.builder()
                                .rolName(rolName)
                                .build()
                );
            } catch (Exception ex) {
                //log.info("Error creating roles: {}", ex.getMessage());
            }
        });

        log.info("Created roles");
    }

    private final RolService rolService;
}