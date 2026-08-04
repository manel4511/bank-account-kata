package com.sg.kata.bankaccount.adapter.in.web.dto;

import com.sg.kata.bankaccount.domain.model.OperationType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountOperationResponse(
        OperationType type,
        LocalDate date,
        BigDecimal amount,
        BigDecimal balance
) {
}