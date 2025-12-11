package com.kojstarinnovations.terminal.auths.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.dto.authservice.JwtDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.LoginUser;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.RefreshTokenRequest;

import java.util.Optional;

/**
 * AuthUC - Use Case Interface for Authentication
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AuthUC {

    Optional<UserResponse> getByEmail(String email);

    JwtDTO login(LoginUser loginUser, String deviceUserAgent, String deviceIp);

    void logout(String refreshToken);

    JwtDTO refreshTokenWithout2fA(RefreshTokenRequest request, String deviceUserAgent, String deviceIp);

    JwtDTO refreshToken(RefreshTokenRequest request, String deviceUserAgent, String deviceIp);
}