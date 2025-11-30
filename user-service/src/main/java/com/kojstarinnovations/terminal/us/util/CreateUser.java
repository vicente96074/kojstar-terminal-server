package com.kojstarinnovations.terminal.us.util;

import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.Gender;
import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.IssuingAuthority;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.SubdivisionRegionCodeISO;
import com.kojstarinnovations.terminal.us.application.data.request.AddressUSRequest;
import com.kojstarinnovations.terminal.us.application.data.request.ContactUSRequest;
import com.kojstarinnovations.terminal.us.application.data.request.IdentificationUSRequest;
import com.kojstarinnovations.terminal.us.application.data.request.UserRequest;
import com.kojstarinnovations.terminal.us.domain.service.TransactionService;
import com.kojstarinnovations.terminal.us.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * To create resources for the user in the database when the application starts up
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUser {

    /**
     * Method to create the user
     */
    public void run() {
        try {
            userService.saveWithRolesAndAccesses(getUserRequest());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error creating user: {}", e.getMessage());
        }
    }

    /**
     * Method to get the user request
     *
     * @return - user request
     */
    private UserRequest getUserRequest() {
        //Role
        Set<String> roles = new HashSet<>();
        roles.add("super_admin");

        Set<String> accesses = new HashSet<>();
        accesses.add("administration");
        accesses.add("settings");
        accesses.add("twofactor");
        accesses.add("profile");

        /*String transactionId = (String) transactionService.generateTransaction(PrefixCodesISO.USR_TRANSACTION_ID.getCode()).id();*/
        String transactionId = "T0";

        return UserRequest.builder()
                .transactionId(transactionId)
                .firstName("Augusto")
                .middleName("")
                .firstSurname("Vicente")
                .secondSurname("y Vicente")
                .marriageSurname("Vicente")
                .username("vicente96074")
                .email("vicente96074@gmail.com")
                .password("2025")
                .birthDate(LocalDate.of(1998, 5, 7))
                .storeBranchId("")
                .storeId("")
                .identificationUSRequest(
                        IdentificationUSRequest.builder()
                                .transactionId(transactionId)
                                .identificationNumber("44444555554444")
                                .identificationType(IdentificationType.DPI.name())
                                .fullName("Augusto Vicente y Vicente")
                                .birthDate(LocalDate.of(1998, 5, 7))
                                .gender(Gender.MALE.name())
                                .nationalityCode(CountryCodeISO.GT.getCode())
                                .issueDate(LocalDate.of(2015, 5, 7))
                                .expirationDate(LocalDate.of(2025, 5, 7))
                                .issuingAuthority(IssuingAuthority.RENAP.getCode())
                                .imageUrl("")
                                .build()
                )
                .addressUSRequest(
                        AddressUSRequest.builder()
                                .transactionId(transactionId)
                                .address("Casa 28, Condominio los Arcos, Zona 7")
                                .city("Quetzaltenango")
                                .subdivisionRegionCode(SubdivisionRegionCodeISO.GT_QZ_QU.getCode())
                                .regionCode(RegionCodeISO.GT_QZ.getCode())
                                .countryCode(CountryCodeISO.GT.getCode())
                                .postalCode("09001")
                                .latitude(14)
                                .longitude(91)
                                .build()
                )
                .contactUSRequests(
                        List.of(ContactUSRequest.builder()
                                        .transactionId(transactionId)
                                        .contactType(ContactType.EMAIL.name())
                                        .email("vicente96074@gmail.com")
                                        .primaryContact(true)
                                        .build(),
                                ContactUSRequest.builder()
                                        .transactionId(transactionId)
                                        .contactType(ContactType.PHONE.name())
                                        .countryCode(CountryCodeISO.GT.getCode())
                                        .regionCode(RegionCodeISO.GT_QZ.getCode())
                                        .phoneNumber("44834598")
                                        .primaryContact(false)
                                        .build())
                )
                .accessesRequest(accesses)
                .rolesRequest(roles)
                .build();
    }

    private final UserService userService;
    private final TransactionService transactionService;
}