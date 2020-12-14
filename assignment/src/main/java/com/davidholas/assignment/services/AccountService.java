package com.davidholas.assignment.services;

import com.davidholas.assignment.exceptions.BusinessException;
import com.davidholas.assignment.exceptions.InvalidInputException;
import com.davidholas.assignment.exceptions.ResourceNotFoundException;
import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.Account.AccountResource;
import com.davidholas.assignment.model.Currency;
import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.Rates.ExchangeRatesResource;
import com.davidholas.assignment.model.Rates.RatesResource;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    public static final String CANT_RESOLVE_CURRENCY = "Can't resolve currency.";
    public static final String FX_RATES_API = "https://api.exchangeratesapi.io/latest";

    private RestTemplate restTemplate;

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private TransferHistoryRepository transferHistoryRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, TransferHistoryRepository transferHistoryRepository, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transferHistoryRepository = transferHistoryRepository;
        this.restTemplate = restTemplate;
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

    public void addAccount(AccountResource accountResource) {

        Long customerId = accountResource.getCustomer().getId();
        int accountNumber = accountResource.getAccountNumber();

        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (!customerOpt.isPresent()) {
            throw new ResourceNotFoundException("Customer with id: " + customerId + " was not found.");
        }

        Optional<Account> accountOpt = accountRepository.findAccountByNumber(accountNumber);

        if (accountOpt.isPresent()) {
            throw new InvalidInputException("Account with number: " + accountNumber + " already exists.");
        }

        Customer customer = customerOpt.get();

        Account account = new Account(accountNumber, accountResource.getCurrency(), customer);

        accountRepository.save(account);
    }

    public void depositMoney(TransferDetails transferDetails) {

        Long accountId = transferDetails.getDepositAccountId();
        BigDecimal amount = transferDetails.getAmount();

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " was not found."));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    public void withdrawMoney(TransferDetails transferDetails) {

        Long accountId = transferDetails.getWithdrawalAccountId();
        BigDecimal amount = transferDetails.getAmount();

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " was not found."));

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    public void transferMoney(TransferDetails transferDetails) {

        BigDecimal amount = transferDetails.getAmount();
        Long withdrawalAccId = transferDetails.getWithdrawalAccountId();
        Long depositAccId = transferDetails.getDepositAccountId();

        Optional<Account> withdrawalAccountOpt = accountRepository.findById(withdrawalAccId);
        Optional<Account> depositAccountOpt = accountRepository.findById(depositAccId);

        Account withdrawalAccount = withdrawalAccountOpt.orElseThrow(() -> new ResourceNotFoundException("Account with id: " + withdrawalAccId + " was not found."));
        Account depositAccount = depositAccountOpt.orElseThrow(() -> new ResourceNotFoundException("Account with id: " + depositAccId + " was not found."));

        if ((withdrawalAccount.getBalance().subtract(amount)).compareTo(BigDecimal.ZERO) == -1) {
            throw new BusinessException("There is not enough money on an account with id: " + withdrawalAccId);
        }

        String withdrawalCurrency = withdrawalAccount.getCurrency().toString();
        String depositCurrency = depositAccount.getCurrency().toString();

        if(withdrawalCurrency.equals(depositCurrency)) {

            commitTransaction(withdrawalAccount, depositAccount, amount, BigDecimal.ONE);

        } else {

            String uri = FX_RATES_API + "?base=" + withdrawalCurrency;

            ExchangeRatesResource exchangeRates = restTemplate.getForObject(uri, ExchangeRatesResource.class);
            RatesResource ratesResource = exchangeRates.getRates();
            BigDecimal rate;

            try {
                Method getter = RatesResource.class.getDeclaredMethod("get" + validateCurrency(depositCurrency), null);
                rate = (BigDecimal) getter.invoke(ratesResource, null);
            } catch (Exception ex) {
                throw new InvalidInputException(CANT_RESOLVE_CURRENCY);
            }

            commitTransaction(withdrawalAccount, depositAccount, amount, rate);
        }
    }

    public void commitTransaction(Account withdrawalAccount, Account depositAccount, BigDecimal amount, BigDecimal rate) {

        withdrawalAccount.setBalance(withdrawalAccount.getBalance().subtract(amount));
        depositAccount.setBalance(depositAccount.getBalance().add(amount.multiply(rate)));

        TransferHistory transferHistory = new TransferHistory(withdrawalAccount.getId(), depositAccount.getId(), amount, rate);

        accountRepository.save(withdrawalAccount);
        accountRepository.save(depositAccount);

        transferHistoryRepository.save(transferHistory);
    }

    public BigDecimal getBalanceInForeign(String currency, Long accountId) {

        BigDecimal rate;
        Account account = this.getAccountById(accountId);
        BigDecimal balance = account.getBalance();
        String validatedCurrency = StringUtils.capitalize(currency.toLowerCase());

        // Get exchange ratesResource from https://exchangeratesapi.io/
        ExchangeRatesResource exchangeRates = restTemplate.getForObject(FX_RATES_API, ExchangeRatesResource.class);
        RatesResource ratesResource = exchangeRates.getRates();

        // Call getter method from RatesResource class for wanted currency
        try {
            Method getter = RatesResource.class.getDeclaredMethod("get" + validatedCurrency, null);
            rate = (BigDecimal) getter.invoke(ratesResource, null);
        } catch (Exception ex) {
            throw new InvalidInputException(CANT_RESOLVE_CURRENCY);
        }

        BigDecimal result = balance.multiply(rate);

        return result;
    }

    public String validateCurrency(String currency) {

        String validatedCurrency = currency.toLowerCase();
        String result = validatedCurrency.substring(0,1).toUpperCase() + validatedCurrency.substring(1);
        return result;
    }

    public BigDecimal convertCurrency(Currency convertFrom, Currency convertTo, BigDecimal amount) {

        BigDecimal rate;

        ExchangeRatesResource exchangeRates = restTemplate.getForObject(FX_RATES_API + "?base=" + convertFrom.toString(), ExchangeRatesResource.class);
        RatesResource ratesResource = exchangeRates.getRates();

        try {
            Method getter = RatesResource.class.getDeclaredMethod("get" + validateCurrency(convertTo.toString()), null);
            rate = (BigDecimal) getter.invoke(ratesResource, null);
        } catch (Exception ex) {
            throw new InvalidInputException(CANT_RESOLVE_CURRENCY);
        }

        BigDecimal result = amount.multiply(rate);

        return result;
    }

    // Need to find proper place for the method and test the correctness of cron expression
    @Scheduled(cron = "0 0 0 1 * ?")
    public void payFees() {

        BigDecimal fee = BigDecimal.valueOf(2);

        List<Account> accounts = this.getAllAccounts();

        for(Account account : accounts) {

            if(!account.getCurrency().equals(Currency.EUR)) {
                fee = convertCurrency(Currency.EUR, account.getCurrency(), fee);
            }

            account.setBalance(account.getBalance().subtract(fee));
            accountRepository.save(account);
        }
    }
}
