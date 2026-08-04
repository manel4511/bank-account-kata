package com.sg.kata.bankaccount.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @Test
    void should_reject_zero_deposit() {

        Account account = new Account();

        assertThatThrownBy(() ->
                account.deposit(BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be strictly positive");
    }

    @Test
    void should_reject_negative_deposit() {

        Account account = new Account();

        assertThatThrownBy(() ->
                account.deposit(new BigDecimal("-10.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be strictly positive");
    }

    @Test
    void should_reject_null_deposit() {

        Account account = new Account();

        assertThatThrownBy(() ->
                account.deposit(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be strictly positive");
    }
}