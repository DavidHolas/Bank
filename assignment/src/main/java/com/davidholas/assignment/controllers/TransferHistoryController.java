package com.davidholas.assignment.controllers;

import com.davidholas.assignment.model.TransferHistory;
import com.davidholas.assignment.services.TransferHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/transferHistory")
public class TransferHistoryController {

    private TransferHistoryService transferHistoryService;

    public TransferHistoryController(TransferHistoryService transferHistoryService) {
        this.transferHistoryService = transferHistoryService;
    }

    @GetMapping
    @RequestMapping("/{accountId}")
    public ResponseEntity<List<TransferHistory>> getTransferHistory(@PathVariable Long accountId) {

        System.out.println("ID: " + accountId);

        List<TransferHistory> transferHistoryList = transferHistoryService.getTransferHistoryForAccount(accountId);

        return new ResponseEntity<>(transferHistoryList, HttpStatus.OK);
    }
}
