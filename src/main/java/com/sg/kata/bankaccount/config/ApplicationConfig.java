package com.sg.kata.bankaccount.config;

import com.sg.kata.bankaccount.adapter.out.statement.TextStatementPrinter;
import com.sg.kata.bankaccount.application.port.in.AccountUseCase;
import com.sg.kata.bankaccount.application.port.out.StatementPrinter;
import com.sg.kata.bankaccount.application.service.BankAccountService;
import com.sg.kata.bankaccount.domain.model.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationConfig {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    StatementPrinter statementPrinter() {
        return new TextStatementPrinter();
    }

    @Bean
    AccountUseCase accountUseCase(
            StatementPrinter statementPrinter,
            Clock clock
    ) {
        return new BankAccountService(
                new Account(),
                statementPrinter,
                clock
        );
    }
}