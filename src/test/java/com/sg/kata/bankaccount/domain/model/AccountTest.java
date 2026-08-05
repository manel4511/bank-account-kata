package com.sg.kata.bankaccount.domain.model;

import com.sg.kata.bankaccount.domain.exception.InsufficientFundsException;
import com.sg.kata.bankaccount.domain.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private static final LocalDate DATE =
            LocalDate.of(2026, 8, 4);

    @Test
    void should_deposit_money() {
        Account account = new Account();

        account.deposit(new BigDecimal("100.00"), DATE);

        assertThat(account.getBalance())
                .isEqualByComparingTo("100.00");
    }

    @Test
    void should_reject_invalid_deposit_amounts() {
        Account account = new Account();

        assertThatThrownBy(() -> account.deposit(null, DATE))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");

        assertThatThrownBy(() -> account.deposit(BigDecimal.ZERO, DATE))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");

        assertThatThrownBy(() ->
                account.deposit(new BigDecimal("-10.00"), DATE))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");

    }

    @Test
    void should_withdraw_money() {
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"), DATE);

        account.withdraw(new BigDecimal("30.00"), DATE);

        assertThat(account.getBalance())
                .isEqualByComparingTo("70.00");
    }

    @Test
    void should_withdraw_entire_balance() {
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"), DATE);

        account.withdraw(new BigDecimal("100.00"), DATE);

        assertThat(account.getBalance())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void should_reject_withdrawal_when_balance_is_insufficient() {
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"), DATE);

        assertThatThrownBy(() ->
                account.withdraw(new BigDecimal("150.00"), DATE))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds");

        assertThat(account.getBalance())
                .isEqualByComparingTo("100.00");

        assertThat(account.getOperations()).hasSize(1);
    }
    @Test
    void should_reject_invalid_withdrawal_amounts() {
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"), DATE);

        assertThatThrownBy(() -> account.withdraw(null, DATE))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");

        assertThatThrownBy(() -> account.withdraw(BigDecimal.ZERO, DATE))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");

        assertThatThrownBy(() ->
                account.withdraw(new BigDecimal("-10.00"), DATE))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be strictly positive");

        assertThat(account.getBalance())
                .isEqualByComparingTo("100.00");
    }
    @Test
    void should_keep_account_statement_history() {
        Account account = new Account();

        account.deposit(new BigDecimal("100.00"), DATE);
        account.withdraw(new BigDecimal("40.00"), DATE);

        assertThat(account.getOperations()).containsExactly(
                new AccountOperation(
                        OperationType.DEPOSIT,
                        DATE,
                        new BigDecimal("100.00"),
                        new BigDecimal("100.00")
                ),
                new AccountOperation(
                        OperationType.WITHDRAWAL,
                        DATE,
                        new BigDecimal("-40.00"),
                        new BigDecimal("60.00")
                )
        );
    }

    @Test
    void should_return_unmodifiable_operation_history() {
        Account account = new Account();
        account.deposit(new BigDecimal("100.00"), DATE);

        assertThatThrownBy(() -> account.getOperations().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}