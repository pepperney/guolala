package com.guolala.zxx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/15
 * @Description:
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "auth.router")
public class AuthConfig {

    private List<String> chains;


}
