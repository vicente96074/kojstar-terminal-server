package com.kojstarinnovations.terminal.auths.domain.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    @SneakyThrows
    public void sendEmail(Map<String, Object> variables, String template, String mailTo, String subject, Map<String, Object> inlineResources, Locale locale) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        // Configurar contexto de Thymeleaf
        Context context = new Context(locale);
        context.setVariables(variables);

        // Procesar plantilla
        String html = templateEngine.process(template, context);

        // Configurar email
        helper.setTo(mailTo);
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom(sender);

        // Agregar recursos inline de manera más robusta
        if (inlineResources != null) {
            for (Map.Entry<String, Object> entry : inlineResources.entrySet()) {
                if (entry.getValue() instanceof String resourcePath) {
                    helper.addInline(entry.getKey(), new ClassPathResource(resourcePath));
                }
            }
        }

        // Enviar email
        javaMailSender.send(message);
    }

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.sender}")
    private String sender;
}