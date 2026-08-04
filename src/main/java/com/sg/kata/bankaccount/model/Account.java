package com.sg.kata.bankaccount.model;

import com.sg.kata.bankaccount.exception.InsufficientFundsException;
import com.sg.kata.bankaccount.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Operation> operations = new ArrayList<>();

    public void deposit(BigDecimal amount) {

        validateAmount(amount);

        balance = balance.add(amount);

        operations.add(new Operation(
                OperationType.DEPOSIT,
                LocalDate.now(),
                amount,
                balance
        ));
    }

    public void withdraw(BigDecimal amount) {

        validateAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        balance = balance.subtract(amount);

        operations.add(new Operation(
                OperationType.WITHDRAWAL,
                LocalDate.now(),
                amount.negate(),
                balance
        ));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Operation> getOperations() {
        return List.copyOf(operations);
    }

    public String printStatement() {
        StringBuilder statement = new StringBuilder(
                "OPERATION | DATE | AMOUNT | BALANCE"
        );

        for (Operation operation : operations) {
            statement.append(System.lineSeparator())
                    .append(operation.type())
                    .append(" | ")
                    .append(operation.date())
                    .append(" | ")
                    .append(operation.amount())
                    .append(" | ")
                    .append(operation.balance());
        }

        return statement.toString();
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException(
                    "Amount must be strictly positive"
            );
        }
    }
}