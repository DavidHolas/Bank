package com.davidholas.assignment.repositories;

import com.davidholas.assignment.model.ExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, Long> {

    @Query("SELECT ex FROM ExchangeRates ex WHERE ex.date = :date AND ex.base = :base")
    Optional<ExchangeRates> getExchangeRatesByDate(@Param("date") String date, @Param("base") String base);
}
