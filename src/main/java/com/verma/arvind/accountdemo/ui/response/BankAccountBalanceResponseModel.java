package com.verma.arvind.accountdemo.ui.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountBalanceResponseModel {
    private Long accountId;
    private BigDecimal balance;
}
