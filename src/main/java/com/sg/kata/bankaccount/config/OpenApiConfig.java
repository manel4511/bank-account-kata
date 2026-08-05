package com.sg.kata.bankaccount.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankAccountOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Bank Account API")
                                .description("""
                                        REST API implementing the Bank Account Kata.
                                        Supports deposits, withdrawals,
                                        balance consultation and statement printing.
                                        """)
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("new Contact()\n" +
                                                        "        .name(\"Manel Ben Salah\")")
                                )
                );
    }
}