package com.sg.kata.bankaccount.adapter.in.web.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal balance
) {
}