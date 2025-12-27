package com.kojstarinnovations.terminal.us.util;

import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.AccessRequest;
import com.kojstarinnovations.terminal.us.domain.service.AccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * To create resources in the database when the application starts up
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAccess {

    /**
     * Method to create the access
     */
    public void run() {

        List<AccessName> accessNames = Stream.of(AccessName.values()).toList();

        accessNames.forEach(accessName -> {
            try {
                accessService.save(
                        AccessRequest.builder()
                                .accessName(accessName)
                                .build()
                );
            } catch (Exception ex) {
                //log.info("Error creating access: {}", ex.getMessage());
            }
        });

        log.info("Created access");
    }

    private final AccessService accessService;
}