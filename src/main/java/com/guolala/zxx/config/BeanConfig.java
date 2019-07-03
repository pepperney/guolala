package com.guolala.zxx.config;

import com.guolala.zxx.support.GFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.Filter;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
@Configuration
public class BeanConfig {


    @Bean
    @Order(2)
    public Filter gFilter() {
        return new GFilter();
    }


    /**
     * http://host:port/swagger-ui.html
     * https://blog.csdn.net/wyb880501/article/details/79576784
     *
     * @return
     */
    @Bean
    public Docket apiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("guolala-api接口文档").version("1.0").build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.guolala.zxx.controller"))
                .paths(PathSelectors.any())
                .build();
    }


}
