package com.verma.arvind.accountdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BankAccountDto {

    private Long accountId;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance;
}
