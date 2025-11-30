package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.commons.AuthPayload;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.payload.commons.UserAuthenticated;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.preference.SettingDefaultRequest;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import com.kojstarinnovations.terminal.us.application.data.request.UserAccessRequest;
import com.kojstarinnovations.terminal.us.application.data.request.UserRequest;
import com.kojstarinnovations.terminal.us.application.data.request.UserRolRequest;
import com.kojstarinnovations.terminal.us.domain.dmimpl.UserDM;
import com.kojstarinnovations.terminal.us.domain.opextends.UserOP;
import com.kojstarinnovations.terminal.us.domain.ucextends.UserUC;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * UserService - Implementation of the User use case interface for saving, deleting, retrieving, and updating User points.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserUC {

    /**
     * existsById method
     *
     * @param id the id of the entity.
     * @return boolean true if exists otherwise false.
     */
    @Override
    public boolean existsById(String id) {
        return outputPort.existsById(id);
    }

    /**
     * save method
     *
     * @param request the request of the entity.
     * @return response the response of the entity.
     */
    @Override
    public UserResponse save(UserRequest request) {
        if (existsByUsername(request.getUsername())) {
            throw new DuplicateException("El nombre de usuario ya existe");
        }

        if (existsByEmail(request.getEmail())) {
            throw new DuplicateException("El correo electrónico ya existe");
        }

        UserDTO dto = domainMapper.requestToDTO(request);
        dto.setId(null);
        dto = (UserDTO) auditAttributeUSService.getAuditAttributesForSystem(dto); // Add audit attributes for system
        dto.setStoreId(request.getStoreId());
        dto.setStoreBranchId(request.getStoreBranchId());
        dto = outputPort.save(dto);

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * deleteById method
     *
     * @param id the id of the entity.
     */
    @Override
    public void deleteById(String id) {
        outputPort.deleteById(id);
    }

    /**
     * getById method
     *
     * @param ID the id of the entity.
     * @return modelDto the modelDto of the entity.
     */
    @Override
    public UserResponse getById(String ID) {
        Optional<UserDTO> userDto = outputPort.getById(ID);

        return domainMapper.dtoToResponse(userDto.orElseThrow(() -> new NotFoundException("User not found by ID")));
    }

    /**
     * Get all objects
     *
     * @param pageable the pageable object
     * @return Page<Response>
     */
    @Override
    public Page<UserResponse> getPage(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<UserResponse> getAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * exists by username method
     *
     * @param username the username of the entity.
     * @return boolean true if exists otherwise false.
     */
    @Override
    public boolean existsByUsername(String username) {
        return outputPort.existsByUsername(username);
    }

    /**
     * exists by email method
     *
     * @param email the email of the entity.
     * @return boolean true if exists otherwise false.
     */
    @Override
    public boolean existsByEmail(String email) {
        return outputPort.existsByEmail(email);
    }

    /**
     * saveWithBasicRolesAndAccesses method
     *
     * @param request the user request
     * @return userResponse the user response
     */
    @Transactional
    public UserResponse saveWithBasicRolesAndAccesses(UserRequest request) {
        try {
            String transactionId = (String) transactionService.generateTransaction(PrefixCodesISO.USR_TRANSACTION_ID.getCode()).id();
            request.setTransactionId(transactionId);
            request.getIdentificationUSRequest().setTransactionId(transactionId);
            request.getAddressUSRequest().setTransactionId(transactionId);
            request.getContactUSRequests().forEach(contact -> contact.setTransactionId(transactionId));

            request.setPassword(passwordEncoder.encode(request.getPassword()));
            UserResponse userResponse = save(request);

            request.getIdentificationUSRequest().setUserId(userResponse.getId());
            request.getAddressUSRequest().setUserId(userResponse.getId());
            request.getContactUSRequests().forEach(contact -> contact.setUserId(userResponse.getId()));

            identificationUSService.save(request.getIdentificationUSRequest());
            addressUSService.save(request.getAddressUSRequest());
            request.getContactUSRequests().forEach(contactUSService::save);

            Set<String> roles = new HashSet<>();
            roles.add("cashier");

            Set<String> accesses = new HashSet<>();
            accesses.add("cash");

            Set<RolResponse> rolesResponses = getRoleFromDB(roles);
            Set<AccessResponse> accessesResponses = getAccessFromDB(accesses);

            userResponse.setRolResponses(rolesResponses);
            userResponse.setAccessResponses(accessesResponses);

            rolesResponses.forEach(rol -> assignRole(userResponse.getId(), rol.getId()));

            accessesResponses.forEach(access -> assignAccess(userResponse.getId(), access.getId()));

            return userResponse;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * saveWithRolesAndAccesses method
     *
     * @param request the user request
     */
    @Transactional
    public void saveWithRolesAndAccesses(@Valid UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserResponse userResponse = save(request);

        // Set user id
        request.getIdentificationUSRequest().setUserId(userResponse.getId());
        request.getAddressUSRequest().setUserId(userResponse.getId());
        request.getContactUSRequests().forEach(contact -> contact.setUserId(userResponse.getId()));

        // Save identification, address and contacts
        identificationUSService.save(request.getIdentificationUSRequest());
        addressUSService.save(request.getAddressUSRequest());
        request.getContactUSRequests().forEach(contactUSService::save);

        Set<RolResponse> rolesResponses = getRoleFromDB(request.getRolesRequest());
        Set<AccessResponse> accessesResponses = getAccessFromDB(request.getAccessesRequest());

        userResponse.setRolResponses(rolesResponses);
        userResponse.setAccessResponses(accessesResponses);

        rolesResponses.forEach(rol -> assignRole(userResponse.getId(), rol.getId()));

        accessesResponses.forEach(access -> assignAccess(userResponse.getId(), access.getId()));

        SettingDefaultRequest defaultRequest = SettingDefaultRequest.builder()
                .userId(userResponse.getId())
                .transactionId(request.getTransactionId())
                .authPayload(AuthPayload.builder()
                        .userId(userResponse.getId())
                        .storeBranchId(userResponse.getStoreBranchId())
                        .storeId(userResponse.getStoreId())
                        .build())
                .build();

        log.info("SettingDefaultRequest: {}", defaultRequest);
    }

    /**
     * assignRole method
     *
     * @param userId the user id
     * @param roleId the role id
     */
    public void assignRole(String userId, String roleId) {

        boolean existsUserById = existsById(userId);
        boolean existsRolById = rolService.existsById(roleId);

        if (!existsUserById) {
            throw new NotFoundException("User not found by ID");
        }

        if (!existsRolById) {
            throw new NotFoundException("Rol not found by ID");
        }

        UserRolRequest userRolRequest = new UserRolRequest();
        userRolRequest.setUserId(userId);
        userRolRequest.setRolId(roleId);
        userRolService.save(userRolRequest);
    }

    /**
     * assignAccess method
     *
     * @param userId   the user id
     * @param accessId the access id
     */
    public void assignAccess(String userId, String accessId) {

        boolean existsUserById = existsById(userId);
        boolean existsAccessById = accessService.existsById(accessId);

        if (!existsUserById) {
            throw new NotFoundException("User not found by ID");
        }

        if (!existsAccessById) {
            throw new NotFoundException("Access not found by ID");
        }

        UserAccessRequest userAccessRequest = new UserAccessRequest();
        userAccessRequest.setUserId(userId);
        userAccessRequest.setAccessId(accessId);

        userAccessService.save(userAccessRequest);
    }

    /**
     * saveRolesFromRequest method
     *
     * @param roles the roles
     * @return rolesResponses the roles responses
     */
    public Set<RolResponse> getRoleFromDB(Set<String> roles) {
        Set<RolResponse> rolesResponses = new HashSet<>();

        if (roles.contains("super_admin")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.SUPER_ADMIN);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("ceo")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.CEO);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("regional_manager")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.REGIONAL_MANAGER);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("administrator")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.ADMINISTRATOR);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("seller")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.SELLER);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("cashier")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.CASHIER);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("inventory_manager")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.INVENTORY_MANAGER);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("logistic_staff")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.LOGISTIC_STAFF);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("marketing_staff")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.MARKETING_STAFF);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("maintenance_staff")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.MAINTENANCE_STAFF);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("customer")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.CUSTOMER);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("supplier")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.SUPPLIER);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("delivery_person")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.DELIVERY_PERSON);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("security_guard")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.SECURITY_GUARD);
            rolesResponses.add(rolResponse);
        } else if (roles.contains("cleaner")) {
            RolResponse rolResponse = rolService.getByRolName(RolName.CLEANER);
            rolesResponses.add(rolResponse);
        }

        return rolesResponses;
    }

    /**
     * saveAccessFromRequest method
     *
     * @param accesses the accesses
     * @return accessResponses the access responses
     */
    public Set<AccessResponse> getAccessFromDB(Set<String> accesses) {
        Set<AccessResponse> accessResponses = new HashSet<>();

        if (accesses.contains("administration")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.ADMINISTRATION);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("quote")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.QUOTE);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("purchases")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.PURCHASES);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("sales")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.SALES);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("cash")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.CASH);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("inventory")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.INVENTORY);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("suppliers")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.SUPPLIERS);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("clients")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.CLIENTS);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("users")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.USERS);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("reports")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.REPORTS);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("settings")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.SETTINGS);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("warehouse")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.WAREHOUSE);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("logistics")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.LOGISTICS);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("administrative_office")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.ADMINISTRATIVE_OFFICE);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("customer_service")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.CUSTOMER_SERVICE);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("marketing")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.MARKETING);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("human_resources")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.HUMAN_RESOURCES);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("accounting")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.ACCOUNTING);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("finance")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.FINANCE);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("management")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.MANAGEMENT);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("security")) {
            AccessResponse accessResponse = accessService.getByAccessName(AccessName.SECURITY);
            accessResponses.add(accessResponse);
        }
        if (accesses.contains("it")) {
            AccessResponse access = accessService.getByAccessName(AccessName.IT);
            accessResponses.add(access);
        }
        if (accesses.contains("procurement")) {
            AccessResponse access = accessService.getByAccessName(AccessName.PROCUREMENT);
            accessResponses.add(access);
        }
        if (accesses.contains("legal")) {
            AccessResponse access = accessService.getByAccessName(AccessName.LEGAL);
            accessResponses.add(access);
        }
        if (accesses.contains("production")) {
            AccessResponse access = accessService.getByAccessName(AccessName.PRODUCTION);
            accessResponses.add(access);
        }
        if (accesses.contains("operations")) {
            AccessResponse access = accessService.getByAccessName(AccessName.OPERATIONS);
            accessResponses.add(access);
        }
        if (accesses.contains("maintenance")) {
            AccessResponse access = accessService.getByAccessName(AccessName.MAINTENANCE);
            accessResponses.add(access);
        }
        if (accesses.contains("distribution")) {
            AccessResponse access = accessService.getByAccessName(AccessName.DISTRIBUTION);
            accessResponses.add(access);
        }
        if (accesses.contains("twofactor")) {
            AccessResponse access = accessService.getByAccessName(AccessName.TWO_FACTOR);
            accessResponses.add(access);
        }

        if (accesses.contains("profile")) {
            AccessResponse access = accessService.getByAccessName(AccessName.PROFILE);
            accessResponses.add(access);
        }

        return accessResponses;
    }

    /**
     * Get page active by store id
     *
     * @param pageable the pageable object
     * @return Page<UserResponse>
     */
    @Override
    public Page<UserResponse> getPageUserActive(Pageable pageable) {
        Page<UserResponse> page = outputPort.getPageUserActive(pageable).map(domainMapper::dtoToResponse);

        return page;
    }

    public void updateUserSettingId(String userId, String userSettingId) {
        if (userId == null || userId.isEmpty()) {
            throw new NotFoundException("User ID cannot be null or empty");
        }

        if (userSettingId == null || userSettingId.isEmpty()) {
            throw new NotFoundException("User Setting ID cannot be null or empty");
        }

        outputPort.updateUserSettingId(userId, userSettingId);
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id      id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public UserResponse updateById(UserRequest request, String id) {
        if (!existsById(id)) {
            throw new NotFoundException("User not found");
        }

        UserDTO dto = domainMapper.requestToDTO(request);
        dto = (UserDTO) auditAttributeUSService.getAuditAttributesForUpdate(dto);

        dto = outputPort.updateById(dto, id);

        return domainMapper.dtoToResponse(dto);
    }

    public UserAuthenticated getByAuthentication() {
        PrincipalUser principalUser = userDetailsService.getUserFromAuth();

        if (principalUser == null) {
            throw new NotFoundException(ExceptionConstants.NOT_AUTHENTICATED);
        }

        Optional<UserDTO> userDto = outputPort.getById(principalUser.getSub());

        if (userDto.isEmpty()) {
            throw new NotFoundException(ExceptionConstants.USER_NOT_FOUND);
        }

        return UserAuthenticated.of(domainMapper.dtoToResponse(userDto.get()));
    }

    /**
     * Get user by ID
     *
     * @param userId the user ID
     * @return UserResponse the user response
     */
    @Override
    public UserResponse getUserById(String userId) {
        return outputPort.getPayloadById(userId);
    }

    /**
     * Block user
     *
     * @param userId the user ID
     */
    @Override
    public void blockUser(String userId) {
        outputPort.blockUser(userId);
    }

    /**
     * Get status by user id
     *
     * @param userId the user ID
     * @return Status the status
     */
    @Override
    public Status getStatusById(String userId) {
        return outputPort.getStatusById(userId);
    }

    private final UserOP outputPort;
    private final UserDM domainMapper;
    private final RolService rolService;
    private final AccessService accessService;
    private final UserRolService userRolService;
    private final PasswordEncoder passwordEncoder;

    private final IdentificationUSService identificationUSService;
    private final ContactUSService contactUSService;
    private final AddressUSService addressUSService;
    private final UserAccessService userAccessService;
    private final AuditAttributeUSService auditAttributeUSService;
    private final TransactionService transactionService;

    private final UserDetailsServiceImpl userDetailsService;
}