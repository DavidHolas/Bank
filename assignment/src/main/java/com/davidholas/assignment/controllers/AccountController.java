package com.davidholas.assignment.controllers;

import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.Account.AccountResource;
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

    @PostMapping("/addAccount")
    public void addAccount(@RequestBody AccountResource accountResource) {

        accountService.addAccount(accountResource);
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<Double> getBalance(@PathVariable Long accountId) {

        Account account = accountService.getAccountById(accountId);

        double balance = account.getBalance();

        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping("/transferMoney")
    public void transferMoney(@RequestBody TransferDetails transferDetails) {

        accountService.transferMoney(transferDetails);
    }

    @GetMapping
    @RequestMapping("/foreignBalance/{foreignCurrency}/{accountId}")
    public ResponseEntity<Double> getBalanceInForeignCurrency(@PathVariable String foreignCurrency, @PathVariable Long accountId) {

        double result = accountService.getBalanceInForeign(foreignCurrency, accountId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
