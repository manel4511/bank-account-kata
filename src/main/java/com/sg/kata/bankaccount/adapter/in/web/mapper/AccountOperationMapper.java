package com.sg.kata.bankaccount.adapter.in.web.mapper;

import com.sg.kata.bankaccount.adapter.in.web.dto.AccountOperationResponse;
import com.sg.kata.bankaccount.domain.model.AccountOperation;
import org.springframework.stereotype.Component;

@Component
public class AccountOperationMapper {

    public AccountOperationResponse toResponse(
            AccountOperation operation
    ) {
        return new AccountOperationResponse(
                operation.type(),
                operation.date(),
                operation.amount(),
                operation.balance()
        );
    }
}