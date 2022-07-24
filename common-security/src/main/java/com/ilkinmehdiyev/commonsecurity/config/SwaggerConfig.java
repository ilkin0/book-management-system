package com.ilkinmehdiyev.commonsecurity.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Migrate from SpringFox to OpenAPI(SpringDoc)  TODO!!!

    //
    //    @Bean
    //    public Docket api() {
    //        return new Docket(DocumentationType.SWAGGER_2)
    //                .apiInfo(apiInfo())
    //                .securitySchemes(Collections.singletonList(apiKey()))
    //                .securityContexts(securityContext())
    //                .select()
    //                .apis(RequestHandlerSelectors.any())
    //                .paths(PathSelectors.any())
    //                .build();
    //    }
    //
    //    private List<SecurityContext> securityContext() {
    //        return Collections.singletonList(
    //                SecurityContext.builder()
    //                        .securityReferences(defaultAuth())
    //                        .build());
    //    }
    //
    //    private List<SecurityReference> defaultAuth() {
    //        AuthorizationScope authorizationScope = new AuthorizationScope("scope",
    // "accessEverything");
    //
    //        return Collections.singletonList(new SecurityReference("JWT", new
    // AuthorizationScope[]{authorizationScope}));
    //    }
    //
    //    private ApiInfo apiInfo() {
    //        return new ApiInfo(
    //                "API",
    //                "API Documentation",
    //                "1",
    //                "Terms of service",
    //                new Contact("", "", ""),
    //                "License of API",
    //                "API license URL",
    //                Collections.emptyList()
    //        );
    //    }
    //
    //    private ApiKey apiKey() {
    //        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    //    }
}
