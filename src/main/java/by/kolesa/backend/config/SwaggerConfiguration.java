package by.kolesa.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket swaggerConfig() {
    return new Docket(DocumentationType.SWAGGER_2)
        .securitySchemes(List.of(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.basePackage("by.kolesa.backend"))
        .build()
        .apiInfo(createApiInfo())
        .tags(
            new Tag("Auth API", "Endpoints for Authorization", 1),
            new Tag("Question API", "Endpoints for Questions", 2),
            new Tag("Topic API", "Endpoints for Topics", 3),
            new Tag("Chapter API", "Endpoints for Chapters", 4),
            new Tag("Control API", "Endpoints for user's Controls", 5),
            new Tag("Password Reset API", "Endpoints for Reset Password", 6),
            new Tag("Payment API", "Endpoints for Payments", 7));
  }

  private ApiInfo createApiInfo() {
    return new ApiInfo(
        "Kolesa API",
        "API for quiz application for passing tests according to the rules of the road",
        "1.0",
        "Free to use",
        new Contact(
            "Ivan Lukyanchik",
            "https://www.linkedin.com/in/ivan-lukyanchik/",
            "vanek17121999@gmail.com"),
        "API License",
        "Ivan Lukyanchik",
        Collections.emptyList());
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    return List.of(new SecurityReference("JWT", new AuthorizationScope[] {authorizationScope}));
  }
}
