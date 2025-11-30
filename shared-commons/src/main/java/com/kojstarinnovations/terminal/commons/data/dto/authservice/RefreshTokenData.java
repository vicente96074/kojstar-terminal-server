package com.kojstarinnovations.terminal.commons.data.dto.authservice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenData {
    private String tokenId;
    private String sub;
    private String refreshToken;
    private String userAgent;
}