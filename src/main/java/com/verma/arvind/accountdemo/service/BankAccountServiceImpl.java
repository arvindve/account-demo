package com.verma.arvind.accountdemo.service;

import com.verma.arvind.accountdemo.domain.BankAccount;
import com.verma.arvind.accountdemo.domain.Customer;
import com.verma.arvind.accountdemo.dto.BankAccountBalanceDto;
import com.verma.arvind.accountdemo.dto.BankAccountDto;
import com.verma.arvind.accountdemo.dto.TransferAmountDto;
import com.verma.arvind.accountdemo.exception.AccountAlreadyExistException;
import com.verma.arvind.accountdemo.exception.BankAccountNotFoundException;
import com.verma.arvind.accountdemo.exception.InsufficientFundsException;
import com.verma.arvind.accountdemo.repository.BankAccountRepository;
import com.verma.arvind.accountdemo.repository.CustomerRepository;
import com.verma.arvind.accountdemo.ui.request.CreateBankAccountRequestModel;
import com.verma.arvind.accountdemo.ui.request.TransferAmountRequestModel;
import com.verma.arvind.accountdemo.ui.response.BankAccountBalanceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private WebClient.Builder webClientBuilder;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, WebClient.Builder webClientBuilder) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.webClientBuilder = webClientBuilder;
    }


    @Override
    public BankAccountBalanceDto getBankAccountBalance(Long accountId) {

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        if (!optionalBankAccount.isPresent()) {
            throw new BankAccountNotFoundException("bank account not found for account with id : " + accountId);
        }
        BankAccount bankAccount = optionalBankAccount.get();
        BankAccountBalanceDto bankAccountDto = BankAccountBalanceDto.builder()
                .accountId(bankAccount.getId())
                .balance(bankAccount.getBalance())
                .build();
        return bankAccountDto;
    }

    @Override
    @Transactional
    public BankAccountDto createBankAccount(CreateBankAccountRequestModel requestModel) {

        Customer customerByEmail = customerRepository.findByEmail(requestModel.getEmail());

        if (customerByEmail != null) {
            throw new AccountAlreadyExistException("account already exist for email " + requestModel.getEmail());
        }

        BankAccount bankAccount = BankAccount.builder()
                .balance(requestModel.getInitialBalance())
                .build();

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        Customer customer = Customer.builder()
                .firstName(requestModel.getFirstName())
                .lastName(requestModel.getLastName())
                .email(requestModel.getEmail())
                .password(requestModel.getPassword())
                .bankAccount(savedAccount)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        BankAccountDto bankAccountDto = BankAccountDto.builder()
                .accountId(savedAccount.getId())
                .balance(savedAccount.getBalance())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .email(savedCustomer.getEmail())
                .build();
        return bankAccountDto;
    }

    @Override
    //@Transactional(isolation = Isolation.READ_COMMITTED)
    public TransferAmountDto transferAmount(TransferAmountRequestModel transferAmountRequestModel) {

        Long debitAccountId = transferAmountRequestModel.getDebitAccountId();
        Optional<BankAccount> optionalDebitAccount = bankAccountRepository.findById(debitAccountId);
        if (!optionalDebitAccount.isPresent()) {
            throw new BankAccountNotFoundException("bank account not found for accountId: " + debitAccountId);
        }
        Long creditAccountId = transferAmountRequestModel.getCreditAccountId();
        Optional<BankAccount> optionalCreditAccount = bankAccountRepository.findById(creditAccountId);
        if (!optionalCreditAccount.isPresent()) {
            throw new BankAccountNotFoundException("bank account not found for accountId: " + creditAccountId);
        }
        BigDecimal amountToTransfer = transferAmountRequestModel.getAmount();
        BankAccount debitBankAccount = optionalDebitAccount.get();
        if (debitBankAccount.getBalance().subtract(amountToTransfer).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("debit account does not have enough funds to transfer amount current balance " + debitBankAccount.getBalance() +
                    " and requested transfer amount is " + amountToTransfer);
        }

        BigDecimal newDebitAccountBalance = debitBankAccount.getBalance().subtract(amountToTransfer);
        debitBankAccount.setBalance(newDebitAccountBalance);
        bankAccountRepository.saveAndFlush(debitBankAccount);

        BankAccount creditBankAccount = optionalCreditAccount.get();
        BigDecimal newCreditAccountBalance = creditBankAccount.getBalance().add(amountToTransfer);
        creditBankAccount.setBalance(newCreditAccountBalance);
        bankAccountRepository.saveAndFlush(creditBankAccount);


        BankAccountBalanceResponseModel debitAccountResponse = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/account-app-ws/accounts/" + debitAccountId + "/balance")
                .retrieve()
                .bodyToMono(BankAccountBalanceResponseModel.class)
                .block();

        BankAccountBalanceResponseModel creditAccountResponse = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/account-app-ws/accounts/" + creditAccountId + "/balance")
                .retrieve()
                .bodyToMono(BankAccountBalanceResponseModel.class)
                .block();

        TransferAmountDto transferAmountDto = TransferAmountDto.builder()
                .debitAccountId(debitAccountResponse.getAccountId())
                .debitAccountBalance(debitAccountResponse.getBalance())
                .creditAccountId(creditAccountResponse.getAccountId())
                .creditAccountBalance(creditAccountResponse.getBalance())
                .build();
        return transferAmountDto;
    }


}
