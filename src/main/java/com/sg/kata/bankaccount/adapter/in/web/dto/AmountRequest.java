package com.sg.kata.bankaccount.adapter.in.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AmountRequest(

        @NotNull(message = "Amount is required")
        @DecimalMin(
                value = "0.01",
                message = "Amount must be strictly positive"
        )
        @Digits(
                integer = 15,
                fraction = 2,
                message = "Amount must have at most two decimal places"
        )
        BigDecimal amount

) {
}