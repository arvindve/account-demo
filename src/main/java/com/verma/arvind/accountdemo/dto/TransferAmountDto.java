package com.verma.arvind.accountdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferAmountDto {

    private Long debitAccountId;
    private BigDecimal debitAccountBalance;
    private Long creditAccountId;
    private BigDecimal creditAccountBalance;
}
