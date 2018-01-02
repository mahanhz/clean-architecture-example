package com.example.clean.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.swagger.web.UiConfiguration.Constants.NO_SUBMIT_METHODS;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.example.clean.app.web.controller"))
                //.paths(PathSelectors.ant("/**"))
                .build();
    }

    // Make Swagger read-only when in production
    @Bean
    @Profile("production")
    public UiConfiguration uiConfig() {
        return new UiConfiguration(null, NO_SUBMIT_METHODS);
    }
}
