package com.davidholas.assignment.services;

import com.davidholas.assignment.model.TransferHistory;
import com.davidholas.assignment.repositories.TransferHistoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransferHistoryService {

    private TransferHistoryRepository transferHistoryRepository;

    public TransferHistoryService(TransferHistoryRepository transferHistoryRepository) {
        this.transferHistoryRepository = transferHistoryRepository;
    }

    public List<TransferHistory> getTransferHistoryForAccount(Long accountId) {

        List<TransferHistory> transferHistoryList = transferHistoryRepository.getTransferHistoryForAccount(accountId);

        return transferHistoryList;
    }
}
