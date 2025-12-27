package com.kojstarinnovations.terminal.us.infrastructure.adapters.config;

import com.kojstarinnovations.terminal.us.jwt.JwtTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * FeignConfig - Configuration for the Feign
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
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
            String token = request.getHeader("Authorization"); // != null ? request.getHeader("Authorization") : jwtTokenProvider.generateInternalToken();
            if (token != null) {
                requestTemplate.header("Authorization", token);
            }
        }
    }
}