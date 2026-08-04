package com.sg.kata.bankaccount.adapter.in.web;

import com.sg.kata.bankaccount.adapter.in.web.dto.AccountOperationResponse;
import com.sg.kata.bankaccount.adapter.in.web.dto.AmountRequest;
import com.sg.kata.bankaccount.adapter.in.web.dto.BalanceResponse;
import com.sg.kata.bankaccount.adapter.in.web.mapper.AccountOperationMapper;
import com.sg.kata.bankaccount.application.port.in.AccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@Tag(
        name = "Bank Account",
        description = "Deposit, withdrawal, balance and account statement operations"
)
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

    @Operation(
            summary = "Deposit money",
            description = "Deposits a strictly positive amount into the bank account."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deposit completed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid deposit amount"
            )
    })
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

    @Operation(
            summary = "Withdraw money",
            description = "Withdraws a strictly positive amount from the bank account."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Withdrawal completed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid withdrawal amount"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Insufficient funds"
            )
    })
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

    @Operation(
            summary = "Get current balance",
            description = "Returns the current bank account balance."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Current balance returned successfully"
    )
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        return ResponseEntity.ok(
                new BalanceResponse(accountUseCase.getBalance())
        );
    }

    @Operation(
            summary = "Get account statement",
            description = """
                    Returns the account operation history, including the operation type,
                    date, amount and resulting balance.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account statement returned successfully"
    )
    @GetMapping("/statement")
    public ResponseEntity<List<AccountOperationResponse>> getStatement() {
        List<AccountOperationResponse> statement =
                accountUseCase.getStatement()
                        .stream()
                        .map(accountOperationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(statement);
    }

    @Operation(
            summary = "Print account statement",
            description = "Returns the account statement as formatted plain text."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Formatted account statement returned successfully"
    )
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