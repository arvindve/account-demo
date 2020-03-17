package com.verma.arvind.accountdemo.service;

import com.verma.arvind.accountdemo.domain.BankAccount;
import com.verma.arvind.accountdemo.domain.Customer;
import com.verma.arvind.accountdemo.dto.BankAccountBalanceDto;
import com.verma.arvind.accountdemo.exception.AccountAlreadyExistException;
import com.verma.arvind.accountdemo.exception.BankAccountNotFoundException;
import com.verma.arvind.accountdemo.repository.BankAccountRepository;
import com.verma.arvind.accountdemo.repository.CustomerRepository;
import com.verma.arvind.accountdemo.ui.request.CreateBankAccountRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class BankAccountServiceImplTest {

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private CustomerRepository customerRepository;

    private BankAccount bankAccount;
    private Customer customer;


    @BeforeEach
    public void setUp(){
        bankAccount = BankAccount.builder()
                        .id(1234L)
                        .balance(new BigDecimal(500))
                        .build();
        customer = Customer.builder()
                        .firstName("Arvind")
                        .lastName("Verma")
                        .email("arvind.verma.hk@gmail.com")
                        .bankAccount(bankAccount)
                        .build();
    }

    @Test
    public void getAccountBalance_BankAccountNotFoundException(){
        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(BankAccountNotFoundException.class,
                () -> bankAccountService.getBankAccountBalance(123L),
                "Bank account does not exist");
    }
    @Test
    public void getBankAccountBalance_Success(){
        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(bankAccount));
        BankAccountBalanceDto bankAccountBalance = bankAccountService.getBankAccountBalance(123L);
        assertEquals(500.00, bankAccountBalance.getBalance().doubleValue());
    }

    @Test
    public void createBankAccount_AccountAlreadyExistException(){
        when(customerRepository.findByEmail(any(String.class))).thenReturn(customer);
        CreateBankAccountRequestModel request = CreateBankAccountRequestModel.builder()
                                                    .email("arvind.verma@gmail.com")
                                                    .build();
        assertThrows(AccountAlreadyExistException.class,
                () -> bankAccountService.createBankAccount(request),
                "Bank account does not exist");
    }

    @Test
    public void createBankAccount_Success(){
        when(customerRepository.findByEmail(any(String.class))).thenReturn(null);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        CreateBankAccountRequestModel request = CreateBankAccountRequestModel.builder()
                .email("arvind.verma@gmail.com")
                .initialBalance(new BigDecimal(500))
                .firstName("Arvind")
                .lastName("Verma")
                .password("password")
                .build();
        bankAccountService.createBankAccount(request);
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
