package com.kojstarinnovations.terminal.oauth2.infrastructure.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * FeignConfig - Configuration for the Feign
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Configuration
@RequiredArgsConstructor
public class FeignConfig implements RequestInterceptor {

    /**
     * Apply the request template
     *
     * @param requestTemplate the request template
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("X-JWT-Token") != null ? request.getHeader("X-JWT-Token") : jwtProvider.generateInternalToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        }
    }

    private final JwtProvider jwtProvider;
}