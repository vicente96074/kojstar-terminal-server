package com.kojstarinnovations.terminal.us.domain.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SendEmail {

    @Scheduled(cron = "0 45 9 * * *")
    @SneakyThrows
    public void sendEmailTask() {
        // Logic to send email
        System.out.println("Sending scheduled email...");

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        Map<String, Object> model = new HashMap<>();
        model.put("date", LocalDate.now().toString());

        context.setVariables(model);

        String html = templateEngine.process("fixed-term-capitalization-users", context);
        helper.setFrom("Kojstar Terminal <support@kojstarterminal.com>");
        helper.setTo("vicente96074@gmail.com");
        helper.setSubject("Capitalización de Intereses de Cuentas de Ahorro a Plazo Fijo");
        helper.setText(html, true);

        helper.addInline("logoImage", new ClassPathResource("/img/logo.png"));

        javaMailSender.send(message);
    }

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
}
