package com.sg.kata.bankaccount.application.service;

import com.sg.kata.bankaccount.adapter.out.statement.TextStatementPrinter;
import com.sg.kata.bankaccount.application.port.in.AccountUseCase;
import com.sg.kata.bankaccount.domain.model.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-08-04T10:00:00Z"),
            ZoneOffset.UTC
    );

    @Test
    void should_use_injected_clock_for_operation_date() {
        AccountUseCase accountUseCase = createUseCase();

        accountUseCase.deposit(new BigDecimal("100.00"));

        assertThat(accountUseCase.getStatement().getFirst().date())
                .isEqualTo(LocalDate.of(2026, 8, 4));
    }

    @Test
    void should_return_updated_balance_after_deposit() {
        AccountUseCase accountUseCase = createUseCase();

        BigDecimal balance =
                accountUseCase.deposit(new BigDecimal("100.00"));

        assertThat(balance)
                .isEqualByComparingTo("100.00");
    }

    @Test
    void should_return_updated_balance_after_withdrawal() {
        AccountUseCase accountUseCase = createUseCase();

        accountUseCase.deposit(new BigDecimal("100.00"));

        BigDecimal balance =
                accountUseCase.withdraw(new BigDecimal("40.00"));

        assertThat(balance)
                .isEqualByComparingTo("60.00");
    }

    @Test
    void should_print_statement() {
        AccountUseCase accountUseCase = createUseCase();

        accountUseCase.deposit(new BigDecimal("100.00"));
        accountUseCase.withdraw(new BigDecimal("40.00"));

        assertThat(accountUseCase.printStatement())
                .isEqualTo("""
                        OPERATION | DATE | AMOUNT | BALANCE
                        DEPOSIT | 04/08/2026 | 100.00 | 100.00
                        WITHDRAWAL | 04/08/2026 | -40.00 | 60.00""");
    }

    private AccountUseCase createUseCase() {
        return new BankAccountService(
                new Account(),
                new TextStatementPrinter(),
                FIXED_CLOCK
        );
    }
}