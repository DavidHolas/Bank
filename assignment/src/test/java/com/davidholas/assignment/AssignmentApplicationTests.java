package com.davidholas.assignment;

import com.davidholas.assignment.exceptions.BusinessException;
import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.TransferDetails;
import com.davidholas.assignment.repositories.AccountRepository;
import com.davidholas.assignment.repositories.CustomerRepository;
import com.davidholas.assignment.repositories.TransferHistoryRepository;
import com.davidholas.assignment.services.AccountService;
import com.davidholas.assignment.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AssignmentApplicationTests {

	AccountService accountService;

	@Mock
	AccountRepository accountRepository;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	TransferHistoryRepository transferHistoryRepository;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void setUp() {

		Mockito.mock(AccountRepository.class);
		Mockito.mock(CustomerService.class);

		accountService = new AccountService(accountRepository, customerRepository, transferHistoryRepository);
	}

	@Test
	@Rollback
	public void newAccountDefaultBalance() {

		Account account = new Account();
		assertEquals(BigDecimal.valueOf(5000), account.getBalance());
	}

	@Test
	@Rollback
	public void transferMoneyDecreasesWithdrawalAccountBalance() {

		Account withdrawalAccount = new Account();
		withdrawalAccount.setId(2L);
		Account depositAccount = new Account();
		depositAccount.setId(3L);

		Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.ofNullable(withdrawalAccount));
		Mockito.when(accountRepository.findById(3L)).thenReturn(Optional.ofNullable(depositAccount));

		TransferDetails transferDetails = new TransferDetails(withdrawalAccount.getId(), depositAccount.getId(), BigDecimal.valueOf(1000.0));

		accountService.transferMoney(transferDetails);

		assertEquals(BigDecimal.valueOf(4000.0), withdrawalAccount.getBalance());
	}

	@Test
	@Rollback
	public void transferMoneyIncreasesDepositAccountBalance() {

		Account withdrawalAccount = new Account();
		withdrawalAccount.setId(2L);
		Account depositAccount = new Account();
		depositAccount.setId(3L);

		Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.ofNullable(withdrawalAccount));
		Mockito.when(accountRepository.findById(3L)).thenReturn(Optional.ofNullable(depositAccount));

		TransferDetails transferDetails = new TransferDetails(withdrawalAccount.getId(), depositAccount.getId(), BigDecimal.valueOf(1000.0));

		accountService.transferMoney(transferDetails);

		assertEquals(BigDecimal.valueOf(6000.0), depositAccount.getBalance());
	}

	@Test
	public void notEnoughMoneyOnAccountThrowsException() {
		assertThrows(BusinessException.class,
				()->{

					Account withdrawalAccount = new Account();
					withdrawalAccount.setId(2L);
					Account depositAccount = new Account();
					depositAccount.setId(3L);

					Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.ofNullable(withdrawalAccount));
					Mockito.when(accountRepository.findById(3L)).thenReturn(Optional.ofNullable(depositAccount));

					TransferDetails transferDetails = new TransferDetails(2L, 3L, BigDecimal.valueOf(25000.0));

					accountService.transferMoney(transferDetails);
				});
	}



}
