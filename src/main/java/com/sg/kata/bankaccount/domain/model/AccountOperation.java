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
        Objects.requireNonNull(type, "Operation type is required");
        Objects.requireNonNull(date, "Operation date is required");
        Objects.requireNonNull(amount, "Operation amount is required");
        Objects.requireNonNull(balance, "Operation balance is required");
    }
}