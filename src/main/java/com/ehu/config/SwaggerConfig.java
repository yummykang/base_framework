package com.ehu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-02 16:06.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        ApiInfo apiInfo = new ApiInfo(
                "demon Restful API",
                "demon Restful API",
                "1.0",
                "http://ehoo100.com/",
                "Demon",
                "ehoo100",
                "http://ehoo100.com/"
        );

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ehu.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);

        return docket;
    }
}
