package com.verma.arvind.accountdemo.controller;

import com.verma.arvind.accountdemo.dto.BankAccountBalanceDto;
import com.verma.arvind.accountdemo.dto.BankAccountDto;
import com.verma.arvind.accountdemo.dto.TransferAmountDto;
import com.verma.arvind.accountdemo.service.BankAccountService;
import com.verma.arvind.accountdemo.ui.request.CreateBankAccountRequestModel;
import com.verma.arvind.accountdemo.ui.request.TransferAmountRequestModel;
import com.verma.arvind.accountdemo.ui.response.BankAccountBalanceResponseModel;
import com.verma.arvind.accountdemo.ui.response.CreateBankAccountResponseModel;
import com.verma.arvind.accountdemo.ui.response.TransferAmountResponseModel;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final ModelMapper modelMapper;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, ModelMapper modelMapper) {
        this.bankAccountService = bankAccountService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "get account balance by id")
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BankAccountBalanceResponseModel> getAccountBalance(@PathVariable Long accountId) {
        BankAccountBalanceDto bankAccountBalance = bankAccountService.getBankAccountBalance(accountId);
        BankAccountBalanceResponseModel bankAccountBalanceResponseModel = modelMapper.map(bankAccountBalance, BankAccountBalanceResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountBalanceResponseModel);
    }

    @ApiOperation(value = "create a bank account")
    @PostMapping
    public ResponseEntity<CreateBankAccountResponseModel> createAccount(@RequestBody @Valid CreateBankAccountRequestModel accountDetails) {
        BankAccountDto bankAccountDetails = bankAccountService.createBankAccount(accountDetails);
        CreateBankAccountResponseModel createBankAccountResponseModel = modelMapper.map(bankAccountDetails, CreateBankAccountResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createBankAccountResponseModel);
    }

    @ApiOperation(value = "transfer money from debit account to credit account")
    @PutMapping("/transferAmount")
    public ResponseEntity<TransferAmountResponseModel> transferAmount(@RequestBody @Valid TransferAmountRequestModel transferAmountRequestModel) {
        TransferAmountDto transferAmountDto = bankAccountService.transferAmount(transferAmountRequestModel);
        TransferAmountResponseModel transferAmountResponseModel = modelMapper.map(transferAmountDto, TransferAmountResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(transferAmountResponseModel);
    }

}
