package com.awifi.np.admin.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

public class AppInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    /**
     *
     */
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ResourcePropertySource propertySource = null;
        try {
            propertySource = new ResourcePropertySource("classpath:main.properties");
        } catch (IOException e) {
            logger.error("main.properties is not exists");
        }
        applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
    }

}
