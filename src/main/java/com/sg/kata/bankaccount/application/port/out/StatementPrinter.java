package com.sg.kata.bankaccount.application.port.out;

import com.sg.kata.bankaccount.domain.model.AccountOperation;

import java.util.List;

public interface StatementPrinter {

    String print(List<AccountOperation> accountOperations);
}