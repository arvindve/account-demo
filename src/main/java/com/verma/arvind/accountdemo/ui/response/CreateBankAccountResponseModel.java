package com.verma.arvind.accountdemo.ui.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountResponseModel {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance;
}
