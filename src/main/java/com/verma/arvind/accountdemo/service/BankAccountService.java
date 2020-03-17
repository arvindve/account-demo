package com.verma.arvind.accountdemo.service;

import com.verma.arvind.accountdemo.dto.BankAccountBalanceDto;
import com.verma.arvind.accountdemo.dto.BankAccountDto;
import com.verma.arvind.accountdemo.dto.TransferAmountDto;
import com.verma.arvind.accountdemo.ui.request.CreateBankAccountRequestModel;
import com.verma.arvind.accountdemo.ui.request.TransferAmountRequestModel;

public interface BankAccountService {

     BankAccountBalanceDto getBankAccountBalance(Long accountId);
     BankAccountDto createBankAccount(CreateBankAccountRequestModel createBankAccountRequestModel);
     TransferAmountDto transferAmount(TransferAmountRequestModel transferAmountRequestModel);


}
