package com.davidholas.assignment.repositories;

import com.davidholas.assignment.model.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

    @Query("SELECT th FROM TransferHistory th WHERE th.withdrawalAccountId = :accountId OR th.depositAccountId = :accountId")
    List<TransferHistory> getTransferHistoryForAccount(@Param("accountId") Long accountId);
}
