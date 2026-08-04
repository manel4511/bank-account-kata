package com.sg.kata.bankaccount.model;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance = BigDecimal.ZERO;

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.add(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be strictly positive");
        }
    }
    public void withdraw(BigDecimal amount) {

        validateAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        balance = balance.subtract(amount);
    }

}