package com.bcnc.prices.web.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .addServersItem(new Server().url("/"))
        .info(new Info()
            .title("bcnc-price-service")
            .description("Technical test to BCNC Group")
            .contact(buildContact())
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }

  private Contact buildContact() {
    Contact contact = new Contact();
    contact.setName("Juan Carlos Lopez");
    contact.setEmail("juanklopez@gmail.com");
    return contact;
  }
}
