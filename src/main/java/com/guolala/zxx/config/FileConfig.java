package com.guolala.zxx.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @Author: pei.nie
 * @Date:2019/4/15
 * @Description:
 */
@Slf4j
@Configuration
public class FileConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Configuration
    @ConditionalOnMissingClass("org.springframework.cloud.config.client.ConfigClientAutoConfiguration")
    @PropertySource(value = {"${config.file:classpath:config/config.properties}"})
    protected static class EncryptFileConfig {
    }


    private String getProperty(Environment environment, String key, String defaultValue) {
        if (environment.getProperty(key) == null) {
            log.info("config.properties not found for property {}, using default value: {}", key, defaultValue);
        }
        return environment.getProperty(key, defaultValue);
    }


}
