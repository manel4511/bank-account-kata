package com.sg.kata.bankaccount.adapter.out.statement;

import com.sg.kata.bankaccount.application.port.out.StatementPrinter;
import com.sg.kata.bankaccount.domain.model.AccountOperation;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class TextStatementPrinter implements StatementPrinter {

    private static final String HEADER =
            "OPERATION | DATE | AMOUNT | BALANCE";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String print(List<AccountOperation> accountOperations) {
        String rows = accountOperations.stream()
                .map(this::formatOperation)
                .collect(Collectors.joining(System.lineSeparator()));

        return rows.isBlank()
                ? HEADER
                : HEADER + System.lineSeparator() + rows;
    }

    private String formatOperation(AccountOperation accountOperation) {
        return String.format(
                Locale.ROOT,
                "%s | %s | %s | %s",
                accountOperation.type(),
                accountOperation.date().format(DATE_FORMATTER),
                formatMoney(accountOperation.amount()),
                formatMoney(accountOperation.balance())
        );
    }

    private String formatMoney(BigDecimal amount) {
        return amount.setScale(2).toPlainString();
    }
}