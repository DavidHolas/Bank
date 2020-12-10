package com.davidholas.assignment.services;

import com.davidholas.assignment.exceptions.BusinessException;
import com.davidholas.assignment.exceptions.InvalidInputException;
import com.davidholas.assignment.exceptions.ResourceNotFoundException;
import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.ExchangeRatesResource;
import com.davidholas.assignment.model.Rates;
import com.davidholas.assignment.model.TransferDetails;
import com.davidholas.assignment.model.TransferHistory;
import com.davidholas.assignment.repositories.AccountRepository;
import com.davidholas.assignment.repositories.CustomerRepository;
import com.davidholas.assignment.repositories.TransferHistoryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private TransferHistoryRepository transferHistoryRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, TransferHistoryRepository transferHistoryRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transferHistoryRepository = transferHistoryRepository;
    }

    public Account getAccountById(Long accountId) {

        Optional<Account> accountOpt = accountRepository.findById(accountId);

        if (!accountOpt.isPresent()) {
            throw new ResourceNotFoundException("Account with id: " + accountId + " was not found.");
        }

        Account account = accountOpt.get();

        return account;
    }

    public List<Account> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        return accounts;
    }

    public void addAccount(Long customerId) {

        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (!customerOpt.isPresent()) {
            throw new ResourceNotFoundException("Customer with id: " + customerId + " was not found.");
        }

        Customer customer = customerOpt.get();

        Account account = new Account(customer);

        accountRepository.save(account);
    }

    public void transferMoney(TransferDetails transferDetails) {

        double amount = transferDetails.getAmount();
        Long withdrawalAccId = transferDetails.getWithdrawalAccountId();
        Long depositAccId = transferDetails.getDepositAccountId();

        Optional<Account> withdrawalAccountOpt = accountRepository.findById(withdrawalAccId);
        Optional<Account> depositAccountOpt = accountRepository.findById(depositAccId);

        Account withdrawalAccount = withdrawalAccountOpt.orElseThrow(() -> new ResourceNotFoundException("Account with id: " + withdrawalAccId + " was not found."));
        Account depositAccount = depositAccountOpt.orElseThrow(() -> new ResourceNotFoundException("Account with id: " + depositAccId + " was not found."));

        if ((withdrawalAccount.getBalance() - amount) < 0) {
            throw new BusinessException("There is not enough money on an account with id: " + withdrawalAccId);
        }

        withdrawalAccount.setBalance(withdrawalAccount.getBalance() - amount);
        depositAccount.setBalance(depositAccount.getBalance() + amount);

        TransferHistory transferHistory = new TransferHistory(withdrawalAccId, depositAccId, amount);

        accountRepository.save(withdrawalAccount);
        accountRepository.save(depositAccount);

        transferHistoryRepository.save(transferHistory);
    }

    public double getBalanceInForeign(String currency, Long accountId) {

        double rate;
        Account account = this.getAccountById(accountId);
        double balance = account.getBalance();
        String validatedCurrency = StringUtils.capitalize(currency.toLowerCase());

        // Get exchange rates from https://exchangeratesapi.io/
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRatesResource exchangeRates = restTemplate.getForObject("https://api.exchangeratesapi.io/latest", ExchangeRatesResource.class);
        Rates rates = exchangeRates.getRates();

        // Call getter method from Rates class for wanted currency
        try {
            Method getter = Rates.class.getDeclaredMethod("get" + validatedCurrency, null);
            rate = (double) getter.invoke(rates, null);
        } catch (Exception ex) {
            throw new InvalidInputException("Can't resolve currency.");
        }

        double result = balance * rate;

        return result;
    }

    // Need to find proper place for the method and test the correctness of cron expression
    @Scheduled(cron = "0 0 0 1 * ?")
    public void payFees() {

        List<Account> accounts = this.getAllAccounts();

        for(Account account : accounts) {

            account.setBalance(account.getBalance() - 100);
            accountRepository.save(account);
        }
    }
}
