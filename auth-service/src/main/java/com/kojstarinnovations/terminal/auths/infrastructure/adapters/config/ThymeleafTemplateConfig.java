package com.kojstarinnovations.terminal.auths.infrastructure.adapters.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * ThymeleafTemplateConfig - Configuration for the Thymeleaf Template and MessageSource.
 * * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Configuration
public class ThymeleafTemplateConfig {

    /**
     * Define el MessageSource para que Spring use los archivos .properties.
     * Este bean será inyectado en el TemplateEngine.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // Carga desde la ruta especificada. Coincide con tu aplicación.yml si usas la ruta 'i18n/reset-password'.
        messageSource.setBasename("i18n/reset-password");
        messageSource.setDefaultEncoding("UTF-8");
        // CRÍTICO: Permite que Thymeleaf muestre la clave si no la encuentra, en lugar de fallar.
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    private ITemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(1);
        resolver.setCheckExistence(true);
        resolver.setCacheable(false); // Mantener desactivado para pruebas
        return resolver;
    }

    /**
     * Configura el Template Engine de Thymeleaf.
     * @param messageSource El bean MessageSource configurado arriba.
     */
    @Bean
    public SpringTemplateEngine templateEngine(MessageSource messageSource) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(emailTemplateResolver());
        // **CRÍTICO:** CONECTAR el MessageSource con el TemplateEngine.
        templateEngine.setTemplateEngineMessageSource(messageSource);
        return templateEngine;
    }
}