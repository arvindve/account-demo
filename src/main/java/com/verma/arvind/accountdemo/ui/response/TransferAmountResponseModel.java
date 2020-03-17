package com.verma.arvind.accountdemo.ui.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferAmountResponseModel {

    private Long debitAccountId;
    private BigDecimal debitAccountBalance;
    private Long creditAccountId;
    private BigDecimal creditAccountBalance;
}
