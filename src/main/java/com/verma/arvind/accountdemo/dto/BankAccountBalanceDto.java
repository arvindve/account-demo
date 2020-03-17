package com.verma.arvind.accountdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BankAccountBalanceDto {

    private Long accountId;
    private BigDecimal balance;
}
