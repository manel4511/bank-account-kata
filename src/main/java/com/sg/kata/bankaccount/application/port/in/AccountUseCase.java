package com.sg.kata.bankaccount.application.port.in;

import com.sg.kata.bankaccount.domain.model.AccountOperation;

import java.math.BigDecimal;
import java.util.List;

public interface AccountUseCase {

    BigDecimal deposit(BigDecimal amount);

    BigDecimal withdraw(BigDecimal amount);

    BigDecimal getBalance();

    List<AccountOperation> getStatement();

    String printStatement();
}