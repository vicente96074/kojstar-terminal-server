package com.kojstarinnovations.terminal.us.vault;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        // Stores the context when it's ready
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        // Gets any bean from the context
        return context.getBean(beanClass);
    }
}