package by.kolesa.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket swaggerConfig() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("by.kolesa.backend"))
        .build()
        .apiInfo(createApiInfo());
  }

  private ApiInfo createApiInfo() {
    return new ApiInfo(
        "Kolesa API",
        "API for best quiz application for passing tests according to the rules of the road",
        "1.0",
        "Free to use",
        new Contact(
            "Vanya Luk", "https://www.linkedin.com/in/ivan-lukyanchik/", "vanek17121999@gmail.com"),
        "API License",
        "VanyaLuk",
        Collections.emptyList());
  }
}
