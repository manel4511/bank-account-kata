package com.sg.kata.bankaccount.application.service;

import com.sg.kata.bankaccount.application.port.in.AccountUseCase;
import com.sg.kata.bankaccount.application.port.out.StatementPrinter;
import com.sg.kata.bankaccount.domain.model.Account;
import com.sg.kata.bankaccount.domain.model.AccountOperation;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class BankAccountService implements AccountUseCase {

    private final Account account;
    private final StatementPrinter statementPrinter;
    private final Clock clock;

    public BankAccountService(
            Account account,
            StatementPrinter statementPrinter,
            Clock clock
    ) {
        this.account = Objects.requireNonNull(account);
        this.statementPrinter = Objects.requireNonNull(statementPrinter);
        this.clock = Objects.requireNonNull(clock);
    }

    @Override
    public BigDecimal deposit(BigDecimal amount) {
        account.deposit(amount, LocalDate.now(clock));
        return account.getBalance();
    }

    @Override
    public BigDecimal withdraw(BigDecimal amount) {
        account.withdraw(amount, LocalDate.now(clock));
        return account.getBalance();
    }

    @Override
    public BigDecimal getBalance() {
        return account.getBalance();
    }

    @Override
    public List<AccountOperation> getStatement() {
        return account.getOperations();
    }

    @Override
    public String printStatement() {
        return statementPrinter.print(account.getOperations());
    }
}