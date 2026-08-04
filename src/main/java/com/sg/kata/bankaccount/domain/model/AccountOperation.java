package com.sg.kata.bankaccount.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record AccountOperation(
        OperationType type,
        LocalDate date,
        BigDecimal amount,
        BigDecimal balance
) {

    public AccountOperation {
        Objects.requireNonNull(type, "AccountOperation type is required");
        Objects.requireNonNull(date, "AccountOperation date is required");
        Objects.requireNonNull(amount, "AccountOperation amount is required");
        Objects.requireNonNull(balance, "AccountOperation balance is required");
    }
}