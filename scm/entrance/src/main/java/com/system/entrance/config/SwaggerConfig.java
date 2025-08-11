package com.system.entrance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * @author lutong
 * @data 2024-12-20 020 14:10
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig  {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("default")
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.test.MyDemo.api"))  //填写扫描Api接口的包,不填默认扫全部
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(securitySchemes()))
                .securityContexts(Arrays.asList(securityContexts()))
                ;
    }

    /**
     *  配置全局参数
     * @return
     */
    private SecurityScheme securitySchemes() {
        return new ApiKey("token", "token", "header");
    }

    /**
     *  配置所有请求需要携带 Token
     * @return
     */
    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("zui", "zui描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("token", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SCM供应链系统-接口文档")
                .description("SCM供应链系统的接口文档，其他接口文档：http://localhost:9999/scm-demo/swagger-ui.html, http://localhost:9999/scm-demo/doc.html")
                .termsOfServiceUrl("http://localhost:9999/login")
                .contact(new Contact("zui", "http://localhost:9999/login", "2890481787@qq.com"))
                .version("1.0")
                .build();
    }
}
