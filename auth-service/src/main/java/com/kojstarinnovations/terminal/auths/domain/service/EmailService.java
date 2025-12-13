package com.kojstarinnovations.terminal.auths.domain.service;

import com.kojstarinnovations.terminal.commons.data.transport.mail.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    @SneakyThrows
    public void sendEmail(EmailRequest request) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        // Configure thymeleaf context
        Context context = new Context(request.getLocale());
        context.setVariables(request.getVariables());

        // Process template
        String html = templateEngine.process(request.getTemplate(), context);

        // Email configuration
        helper.setTo(request.getMailTo());
        helper.setText(html, true);
        helper.setSubject(request.getSubject());
        helper.setFrom(request.getSender());

        // Add inline resources more robustly
        if (request.getInlineResources() != null) {
            for (Map.Entry<String, Object> entry : request.getInlineResources().entrySet()) {
                if (entry.getValue() instanceof String resourcePath) {
                    helper.addInline(entry.getKey(), new ClassPathResource(resourcePath));
                }
            }
        }

        // Send email
        javaMailSender.send(message);
    }

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
}