package com.sg.kata.bankaccount.domain.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(
            BigDecimal currentBalance,
            BigDecimal requestedAmount
    ) {
        super(
                "Insufficient funds. Current balance: %s, requested amount: %s"
                        .formatted(currentBalance, requestedAmount)
        );
    }
}