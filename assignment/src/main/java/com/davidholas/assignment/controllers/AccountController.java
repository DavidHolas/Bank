package com.davidholas.assignment.controllers;

import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.TransferDetails;
import com.davidholas.assignment.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Account> getAccountById(Long accountId) {

        Account account = accountService.getAccountById(accountId);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<Account>> getAllAccounts() {

        List<Account> accounts = accountService.getAllAccounts();

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/addAccount/{customerId}")
    public void addAccount(@PathVariable Long customerId) {

        accountService.addAccount(customerId);
    }

    @GetMapping("/balance/{accountId}")
    public double getBalance(@PathVariable Long accountId) {

        Account account = accountService.getAccountById(accountId);

        double balance = account.getBalance();

        return balance;
    }

    @PutMapping
    @RequestMapping("/transferMoney")
    public void transferMoney(@RequestBody TransferDetails transferDetails) {

        accountService.transferMoney(transferDetails);
    }
}
