package com.sg.kata.bankaccount.adapter.in.web;

import com.sg.kata.bankaccount.application.port.in.AccountUseCase;
import com.sg.kata.bankaccount.domain.exception.InsufficientFundsException;
import com.sg.kata.bankaccount.domain.model.AccountOperation;
import com.sg.kata.bankaccount.domain.model.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import({
        com.sg.kata.bankaccount.adapter.in.web.exception.GlobalExceptionHandler.class,
        com.sg.kata.bankaccount.adapter.in.web.mapper.AccountOperationMapper.class
})
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountUseCase accountUseCase;

    @Test
    void should_deposit_money() throws Exception {

        when(accountUseCase.deposit(new BigDecimal("100.00")))
                .thenReturn(new BigDecimal("100.00"));

        mockMvc.perform(
                        post("/accounts/deposits")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "amount":100.00
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    void should_withdraw_money() throws Exception {

        when(accountUseCase.withdraw(new BigDecimal("40.00")))
                .thenReturn(new BigDecimal("60.00"));

        mockMvc.perform(
                        post("/accounts/withdrawals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "amount":40.00
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(60.00));
    }

    @Test
    void should_return_current_balance() throws Exception {

        when(accountUseCase.getBalance())
                .thenReturn(new BigDecimal("60.00"));

        mockMvc.perform(
                        get("/accounts/balance")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(60.00));
    }

    @Test
    void should_return_statement() throws Exception {

        when(accountUseCase.getStatement())
                .thenReturn(
                        List.of(
                                new AccountOperation(
                                        OperationType.DEPOSIT,
                                        LocalDate.of(2026,8,4),
                                        new BigDecimal("100.00"),
                                        new BigDecimal("100.00")
                                )
                        )
                );

        mockMvc.perform(
                        get("/accounts/statement")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("DEPOSIT"))
                .andExpect(jsonPath("$[0].amount").value(100.00))
                .andExpect(jsonPath("$[0].balance").value(100.00));
    }

    @Test
    void should_print_statement() throws Exception {

        when(accountUseCase.printStatement())
                .thenReturn("""
                        OPERATION | DATE | AMOUNT | BALANCE
                        DEPOSIT | 04/08/2026 | 100.00 | 100.00
                        """);

        mockMvc.perform(
                        get("/accounts/statement/print")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("DEPOSIT")));
    }

    @Test
    void should_return_bad_request_for_invalid_amount() throws Exception {

        mockMvc.perform(
                        post("/accounts/deposits")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "amount":0
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.amount")
                        .value("Amount must be strictly positive"));
    }

    @Test
    void should_return_conflict_when_balance_is_insufficient() throws Exception {

        when(accountUseCase.withdraw(new BigDecimal("1000")))
                .thenThrow(new InsufficientFundsException(
                        new BigDecimal("60"),
                        new BigDecimal("1000")
                ));

        mockMvc.perform(
                        post("/accounts/withdrawals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "amount":1000
                                        }
                                        """)
                )
                .andExpect(status().isConflict());
    }

}