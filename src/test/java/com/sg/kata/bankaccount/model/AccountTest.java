package com.sg.kata.bankaccount.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    @Test
    void should_deposit_money() {
        // Given
        Account account = new Account();

        // When
        account.deposit(new BigDecimal("100.00"));

        // Then
        assertThat(account.getBalance())
                .isEqualByComparingTo(new BigDecimal("100.00"));
    }
}