package dev.wonsama.payment.config;

import java.net.UnknownHostException;

import org.springdoc.core.models.GroupedOpenApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SwaggerConfig {

  final String SWAGGER_TITLE = "payment";
  final String SWAGGER_GROUP = "payment system";
  final String[] packagesToScan = { "dev.wonsama.payment" };

  final String SWAGGER_VERSION = "1.0";
  final String[] paths = { "/api/**" };

  @Bean
  public OpenAPI customOpenAPI() {

    // http://localhost:8080/swagger-ui/index.html

    return new OpenAPI()
        .info(new Info()
            .title(SWAGGER_TITLE)
            .version(SWAGGER_VERSION)
            .description(String.format("%s API", SWAGGER_TITLE)));
  }

  @Bean
  public GroupedOpenApi api() throws UnknownHostException {

    return GroupedOpenApi.builder().group(SWAGGER_GROUP)
        .pathsToMatch(paths)
        .packagesToScan(packagesToScan)
        .build();
  }
}