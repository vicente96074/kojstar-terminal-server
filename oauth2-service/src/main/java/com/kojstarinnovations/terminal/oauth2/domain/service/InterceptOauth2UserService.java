package com.kojstarinnovations.terminal.oauth2.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kojstarinnovations.terminal.commons.data.constants.I18nOAuth2Constants;
import com.kojstarinnovations.terminal.commons.data.enums.*;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GithubUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GoogleUserResponse;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GithubUserDTO;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GoogleUserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InterceptOauth2UserService {

    @SneakyThrows
    public void intercept(JsonNode stateJson, String email, String provider, String storeId, Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                resolveGoogleUser(stateJson, email, storeId, attributes);
                break;
            case "github":
                resolveGithubUser(stateJson, email, attributes);
                break;
            default:
                throw new IllegalStateException(I18nOAuth2Constants.EXCEPTION_UNEXPECTED_PROVIDER);
        }
    }

    @SneakyThrows
    private void resolveGoogleUser(JsonNode stateJson, String email, String storeId, Map<String, Object> attributes) {
        String clientVersion = stateJson.path("clientVersion").asText(null);
        String deviceInfo = stateJson.path("deviceInfo").asText(null);

        if (storeId == null || storeId.isEmpty()) {
            throw new NotFoundException(I18nOAuth2Constants.EXCEPTION_GOOGLE_USER_STORE_ID_NOT_FOUND);
        }

        if (googleUserService.existsByEmail(email)) {
            return;
            //throw new DuplicateException(I18nOAuth2Constants.EXCEPTION_GOOGLE_USER_EMAIL_ALREADY_EXISTS);
        }

        GoogleUserResponse googleUser = googleUserService.save(
                GoogleUserDTO.builder()
                        .id(UUIDHelper.generateUUID(storeId + PrefixCodesISO.GOOGLE_USER.getCode(), 10))
                        .email(email)
                        .name(attributes.get("given_name").toString())
                        .givenName(attributes.get("name").toString())
                        .familyName(attributes.get("family_name").toString())
                        .storeId(storeId)
                        .clientVersion(clientVersion)
                        .deviceInfo(deviceInfo)
                        .status(Status.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .elementStatus(ElementStatus.NEW)
                        .createdBy("System")
                        .build()
        );

        List<AccessName> accessNames = List.of(
                AccessName.GOOGLE_PROFILE,
                AccessName.PROFILE,
                AccessName.TWO_FACTOR
        );

        List<RolName> rolNames = List.of(
                RolName.GOOGLE_USER
        );

        googleUserAccessService.saveList(accessNames, googleUser.getId());
        googleUserRolService.saveList(rolNames, googleUser.getId());
    }

    @SneakyThrows
    private void resolveGithubUser(JsonNode stateJson, String email, Map<String, Object> attributes) {
        String storeId = stateJson.path("storeId").asText(null);
        String clientVersion = stateJson.path("clientVersion").asText(null);
        String deviceInfo = stateJson.path("deviceInfo").asText(null);

        if (storeId == null || storeId.isEmpty()) {
            throw new NotFoundException(I18nOAuth2Constants.EXCEPTION_GITHUB_USER_STORE_ID_NOT_FOUND);
        }

        if (githubUserService.existsByEmail(email)) {
            throw new DuplicateException(I18nOAuth2Constants.EXCEPTION_GITHUB_USER_EMAIL_ALREADY_EXISTS);
        }

        GithubUserResponse response = githubUserService.save(
                GithubUserDTO.builder()
                        .id(attributes.get("id").toString())
                        .email(email)
                        .name(attributes.get("name") != null ? attributes.get("name").toString() : attributes.get("login").toString())
                        .login(attributes.get("login").toString())
                        .storeId(storeId)
                        .clientVersion(clientVersion)
                        .deviceInfo(deviceInfo)
                        .status(Status.ACTIVE)
                        .build()
        );

        List<AccessName> accessNames = List.of(
                AccessName.GITHUB_PROFILE,
                AccessName.PROFILE,
                AccessName.TWO_FACTOR
        );

        List<RolName> rolNames = List.of(
                RolName.GITHUB_USER
        );

        githubUserAccessService.saveList(accessNames, response.getId());
        githubUserRolService.saveList(rolNames, response.getId());
    }

    private final GoogleUserService googleUserService;
    private final GithubUserService githubUserService;
    private final GoogleUserAccessService googleUserAccessService;
    private final GoogleUserRolService googleUserRolService;
    private final GithubUserAccessService githubUserAccessService;
    private final GithubUserRolService githubUserRolService;
}