package com.jukusoft.mmo.main.config;

import com.jukusoft.mmo.main.config.condition.SwaggerCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("default")
public class Swagger2Config {

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.contact.name}")
    private String contactName;

    @Value("${swagger.contact.url}")
    private String contactUrl;

    @Value("${swagger.contact.mail}")
    private String contactMail;

    @Value("${swagger.license.name}")
    private String licenseName;

    @Value("${swagger.license.url}")
    private String licenseUrl;

    @Value("${swagger.version}")
    private String version;

    @Bean
    @Conditional(SwaggerCondition.class)
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.jukusoft.mmo"))
                .paths(PathSelectors.any())//PathSelectors.regex("/.*")
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(title)
                .description(description)
                .contact(new Contact(contactName, contactUrl, contactMail))
                .license(licenseName)
                .licenseUrl(licenseUrl)
                .version(version)
                .build();
    }

}
