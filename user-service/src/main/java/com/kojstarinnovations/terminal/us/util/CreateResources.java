package com.kojstarinnovations.terminal.us.util;

import com.kojstarinnovations.terminal.us.domain.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * To create resources in the database when the application starts up
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class CreateResources implements CommandLineRunner {

    /**
     * Method to create the resources, access, roles and user
     *
     * @param args - arguments
     */
    @Override
    public void run(String... args) {

        /*UserDetails userDetails = userDetailsService.loadDefault();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
*/
        createAccess.run();
        createRoles.run();
        createUser.run();
    }

    private final CreateAccess createAccess;
    private final CreateRoles createRoles;
    private final CreateUser createUser;
    private final UserDetailsServiceImpl userDetailsService;
}