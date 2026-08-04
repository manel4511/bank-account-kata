package com.sg.kata.bankaccount.adapter.in.web;

import com.sg.kata.bankaccount.adapter.in.web.dto.AccountOperationResponse;
import com.sg.kata.bankaccount.adapter.in.web.dto.AmountRequest;
import com.sg.kata.bankaccount.adapter.in.web.dto.BalanceResponse;
import com.sg.kata.bankaccount.adapter.in.web.mapper.AccountOperationMapper;
import com.sg.kata.bankaccount.application.port.in.AccountUseCase;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountUseCase accountUseCase;
    private final AccountOperationMapper accountOperationMapper;

    public AccountController(
            AccountUseCase accountUseCase,
            AccountOperationMapper accountOperationMapper
    ) {
        this.accountUseCase = accountUseCase;
        this.accountOperationMapper = accountOperationMapper;
    }

    @PostMapping("/deposits")
    public ResponseEntity<BalanceResponse> deposit(
            @Valid @RequestBody AmountRequest request
    ) {
        BigDecimal updatedBalance =
                accountUseCase.deposit(request.amount());

        return ResponseEntity.ok(
                new BalanceResponse(updatedBalance)
        );
    }

    @PostMapping("/withdrawals")
    public ResponseEntity<BalanceResponse> withdraw(
            @Valid @RequestBody AmountRequest request
    ) {
        BigDecimal updatedBalance =
                accountUseCase.withdraw(request.amount());

        return ResponseEntity.ok(
                new BalanceResponse(updatedBalance)
        );
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        return ResponseEntity.ok(
                new BalanceResponse(
                        accountUseCase.getBalance()
                )
        );
    }

    @GetMapping("/statement")
    public ResponseEntity<List<AccountOperationResponse>> getStatement() {
        List<AccountOperationResponse> statement =
                accountUseCase.getStatement()
                        .stream()
                        .map(accountOperationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(statement);
    }

    @GetMapping(
            value = "/statement/print",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> printStatement() {
        return ResponseEntity.ok(
                accountUseCase.printStatement()
        );
    }
}