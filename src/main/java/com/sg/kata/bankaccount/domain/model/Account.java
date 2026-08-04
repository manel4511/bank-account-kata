package com.sg.kata.bankaccount.domain.model;

import com.sg.kata.bankaccount.domain.exception.InsufficientFundsException;
import com.sg.kata.bankaccount.domain.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Account {

    private BigDecimal balance = BigDecimal.ZERO;
    private final List<AccountOperation> accountOperations = new ArrayList<>();

    public void deposit(BigDecimal amount, LocalDate date) {
        validateAmount(amount);
        validateDate(date);

        balance = balance.add(amount);

        accountOperations.add(new AccountOperation(
                OperationType.DEPOSIT,
                date,
                amount,
                balance
        ));
    }

    public void withdraw(BigDecimal amount, LocalDate date) {
        validateAmount(amount);
        validateDate(date);

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(balance, amount);
        }

        balance = balance.subtract(amount);

        accountOperations.add(new AccountOperation(
                OperationType.WITHDRAWAL,
                date,
                amount.negate(),
                balance
        ));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<AccountOperation> getOperations() {
        return List.copyOf(accountOperations);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException(
                    "Amount must be strictly positive"
            );
        }

    }

    private void validateDate(LocalDate date) {
        Objects.requireNonNull(date, "AccountOperation date is required");
    }
}