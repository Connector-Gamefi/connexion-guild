package com.connexion.cps.config.swagger;

import com.connexion.cps.config.sys.connexionConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2
 * 
 * 
 */
@EnableOpenApi
@Configuration
public class SwaggerConfig
{

    @Value("${swagger.enabled}")
    private boolean enabled;

    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.OAS_30)
                .enable(enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                // set tital
                .title("openAPI")
                // set descption
                .description("openAPI Document")
                // author
                .contact(new Contact(connexionConfig.getName(), null, null))
                // version
                .version("version:" + connexionConfig.getVersion())
                .build();
    }
}
