package com.sg.kata.bankaccount.model;

import com.sg.kata.bankaccount.exception.InsufficientFundsException;
import com.sg.kata.bankaccount.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

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
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");
    }

    @Test
    void should_reject_negative_deposit() {
        Account account = new Account();

        assertThatThrownBy(() ->
                account.deposit(new BigDecimal("-10.00")))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");
    }

    @Test
    void should_reject_null_deposit() {
        Account account = new Account();

        assertThatThrownBy(() ->
                account.deposit(null))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");
    }

    @Test
    void should_withdraw_money() {
        // Given
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"));

        // When
        account.withdraw(new BigDecimal("30.00"));

        // Then
        assertThat(account.getBalance())
                .isEqualByComparingTo(new BigDecimal("70.00"));
    }

    @Test
    void should_withdraw_the_entire_balance() {
        // Given
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"));

        // When
        account.withdraw(new BigDecimal("100.00"));

        // Then
        assertThat(account.getBalance())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void should_reject_withdrawal_when_balance_is_insufficient() {
        // Given
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"));

        // When / Then
        assertThatThrownBy(() ->
                account.withdraw(new BigDecimal("150.00")))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessage("Insufficient funds");

        assertThat(account.getBalance())
                .isEqualByComparingTo(new BigDecimal("100.00"));
    }
    @Test
    void should_keep_account_statement_history() {

        // Given
        Account account = new Account();

        // When
        account.deposit(new BigDecimal("100.00"));
        account.withdraw(new BigDecimal("40.00"));

        // Then
        assertThat(account.getOperations()).hasSize(2);

        Operation deposit = account.getOperations().get(0);

        assertThat(deposit.type()).isEqualTo(OperationType.DEPOSIT);
        assertThat(deposit.date()).isEqualTo(LocalDate.now());
        assertThat(deposit.amount())
                .isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(deposit.balance())
                .isEqualByComparingTo(new BigDecimal("100.00"));

        Operation withdrawal = account.getOperations().get(1);

        assertThat(withdrawal.type()).isEqualTo(OperationType.WITHDRAWAL);
        assertThat(withdrawal.date()).isEqualTo(LocalDate.now());
        assertThat(withdrawal.amount())
                .isEqualByComparingTo(new BigDecimal("-40.00"));
        assertThat(withdrawal.balance())
                .isEqualByComparingTo(new BigDecimal("60.00"));
    }
}
