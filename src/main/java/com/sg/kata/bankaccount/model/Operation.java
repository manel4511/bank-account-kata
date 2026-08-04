package com.sg.kata.bankaccount.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Operation(
        OperationType type,
        LocalDate date,
        BigDecimal amount,
        BigDecimal balance
) {
}