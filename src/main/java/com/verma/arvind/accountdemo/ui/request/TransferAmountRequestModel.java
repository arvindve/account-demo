package com.verma.arvind.accountdemo.ui.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
public class TransferAmountRequestModel {

    @NotNull(message="Debit Account Id cannot be null")
    private Long debitAccountId;
    @NotNull(message="Credit Account Id cannot be null")
    private Long creditAccountId;
    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal amount;

}
