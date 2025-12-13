package com.kojstarinnovations.terminal.auths.domain.service;

import com.kojstarinnovations.terminal.auths.domain.opextends.ForgotPasswordOP;
import com.kojstarinnovations.terminal.auths.domain.ucextends.AuthUC;
import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.ForgotPasswordDTO;
import com.kojstarinnovations.terminal.commons.data.helper.CodeHelper;
import com.kojstarinnovations.terminal.commons.data.helper.TokenHelper;
import com.kojstarinnovations.terminal.commons.data.payload.commons.TokenJson;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.mail.EmailRequest;
import com.kojstarinnovations.terminal.commons.data.transport.mail.ForgotPasswordRequest;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * AuthEmailService - Service to send emails for authentication purposes.
 * Every transaction is audited.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthEmailService {

    /**
     * Method to send an email for forgot password
     *
     * @param request the dto with the email information
     * @return TokenJson the token
     */
    @SneakyThrows
    public TokenJson sendEmailForgotPassword(ForgotPasswordRequest request) {
        // Validate request
        validatorRequestsService.validateForgotPasswordRequest(request);

        UserResponse user = authService.getByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(I18nCommonConstants.EXCEPTION_THIS_EMAIL_NOT_REGISTERED));

        String token = TokenHelper.generateToken();
        String code = CodeHelper.generateCode();

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", user.getUsername());
        variables.put("url", request.getClientUrl() + "/authentication/reset-password?token=" + token);
        List<String> codeChars = new ArrayList<>();
        for (char c : code.toCharArray()) {
            codeChars.add(String.valueOf(c));
        }
        variables.put("codeChars", codeChars); // PASSING CODE AS SIMPLE STRING

        String template = "user-forgot-password";
        String mailTo = request.getEmail();

        Map<String, Object> lines = new HashMap<>();

        lines.put("logo_image", "/images/Logo.png");

        LocalDateTime now = LocalDateTime.now();
        ForgotPasswordDTO dto = ForgotPasswordDTO.builder()
                .username(user.getUsername())
                .userId(user.getId())
                .email(user.getEmail())
                .token(token)
                .code(code)
                .creationDate(now)
                .expirationDate(now.plusMinutes(30))
                .used(false)
                .build();

        // Use 'en' as default, which is consistent with the 'en' property file suffix
        Locale locale = Locale.forLanguageTag(request.getLanguageTag() != null ? request.getLanguageTag() : "en");
        String subjectKey = messageSource.getMessage("email.reset.subject", null, locale);

        forgotPasswordOP.save(dto);

        // Pass the subject KEY and the Locale
        emailService.sendEmail(
                EmailRequest.builder()
                        .variables(variables)
                        .template(template)
                        .mailTo(mailTo)
                        .subject(subjectKey)
                        .inlineResources(lines)
                        .locale(locale)
                        .sender(supportSender)
                        .build()
        );

        return TokenJson.of(token);
    }

    private final EmailService emailService;
    private final MessageSource messageSource;
    private final AuthUC authService;
    private final ForgotPasswordOP forgotPasswordOP;
    private final ValidatorRequestsService validatorRequestsService;

    @Value("${spring.mail.sender.support}")
    private String supportSender;
}