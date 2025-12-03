package com.kojstarinnovations.terminal.auths.vault;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // Almacena el contexto cuando está listo
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        // Obtiene cualquier bean del contexto
        return context.getBean(beanClass);
    }
}