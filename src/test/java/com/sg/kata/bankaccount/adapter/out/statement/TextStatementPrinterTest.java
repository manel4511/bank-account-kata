package com.sg.kata.bankaccount.adapter.out.statement;

import com.sg.kata.bankaccount.application.port.out.StatementPrinter;
import com.sg.kata.bankaccount.domain.model.AccountOperation;
import com.sg.kata.bankaccount.domain.model.OperationType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TextStatementPrinterTest {

    private final StatementPrinter statementPrinter =
            new TextStatementPrinter();

    @Test
    void should_print_statement() {
        LocalDate date = LocalDate.of(2026, 8, 4);

        List<AccountOperation> accountOperations = List.of(
                new AccountOperation(
                        OperationType.DEPOSIT,
                        date,
                        new BigDecimal("100.00"),
                        new BigDecimal("100.00")
                ),
                new AccountOperation(
                        OperationType.WITHDRAWAL,
                        date,
                        new BigDecimal("-40.00"),
                        new BigDecimal("60.00")
                )
        );

        assertThat(statementPrinter.print(accountOperations))
                .isEqualTo("""
                        OPERATION | DATE | AMOUNT | BALANCE
                        DEPOSIT | 04/08/2026 | 100.00 | 100.00
                        WITHDRAWAL | 04/08/2026 | -40.00 | 60.00""");
    }

    @Test
    void should_print_only_header_when_statement_is_empty() {
        assertThat(statementPrinter.print(List.of()))
                .isEqualTo("OPERATION | DATE | AMOUNT | BALANCE");
    }
}