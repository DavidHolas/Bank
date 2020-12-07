package com.davidholas.assignment.services;

import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.TransferDetails;
import com.davidholas.assignment.model.TransferHistory;
import com.davidholas.assignment.repositories.AccountRepository;
import com.davidholas.assignment.repositories.CustomerRepository;
import com.davidholas.assignment.repositories.TransferHistoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

        Optional<Account> account = accountRepository.findById(accountId);

        return account.get();
    }

    public List<Account> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        return accounts;
    }

    public void addAccount(Long customerId) {

        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if(!customerOpt.isPresent()) {
            throw new RuntimeException("Customer with id: " + customerId + " was not found.");
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

        if(!withdrawalAccountOpt.isPresent()) {
            throw new RuntimeException("Account with id: " + withdrawalAccId + " was not found.");
        }

        if(!depositAccountOpt.isPresent()) {
            throw new RuntimeException("Account with id: " + depositAccId + " was not found.");
        }

        Account withdrawalAccount = withdrawalAccountOpt.orElseThrow(() -> new RuntimeException("Account with id: " + withdrawalAccId + " was not found."));
        Account depositAccount = depositAccountOpt.orElseThrow(() -> new RuntimeException("Account with id: " + depositAccId + " was not found."));

        if((withdrawalAccount.getBalance() - amount) < 0) {
            throw new RuntimeException("There is not enough money on an account with id: " + withdrawalAccId);
        }

        withdrawalAccount.setBalance(withdrawalAccount.getBalance() - amount);
        depositAccount.setBalance(depositAccount.getBalance() + amount);

        TransferHistory transferHistory = new TransferHistory(withdrawalAccId, depositAccId, amount);

        accountRepository.save(withdrawalAccount);
        accountRepository.save(depositAccount);

        transferHistoryRepository.save(transferHistory);
    }
}
