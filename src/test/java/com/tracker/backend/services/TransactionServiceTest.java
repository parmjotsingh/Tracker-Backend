package com.tracker.backend.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.tracker.backend.entities.Transaction;
import com.tracker.backend.entities.User;
import com.tracker.backend.payloads.TransactionDto;
import com.tracker.backend.repositories.TransactionRepo;
import com.tracker.backend.repositories.UserRepo;
import com.tracker.backend.services.impl.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Mock
	TransactionRepo transactionRepo;

	@Mock
	UserRepo userRepo;

	@Mock
	ModelMapper modelMapper;

	ModelMapper mapper = new ModelMapper();

	@InjectMocks
	TransactionServiceImpl transactionService;
	
	// TESTING PERFORMED FOR METHODS OR SERVICE WHICH ARE CURRENTLY USED BY FRONTEND
	
	/*
	 * Testing- fetching all transaction by userId
	 */
	@Test
    void testGetAllTransactionsByUser() {
        User user = new User();
        user.setId(3);

        Transaction t = new Transaction();
        t.setAmount(50L);
        t.setUser(user);
        
        TransactionDto fetchedTransaction = this.mapper.map(t, TransactionDto.class);

        Mockito.when(userRepo.findById(3)).thenReturn(Optional.of(user));
        Mockito.when(transactionRepo.findByUser(user)).thenReturn(List.of(t));
        Mockito.when(modelMapper.map(t, TransactionDto.class)).thenReturn(fetchedTransaction);

        List<TransactionDto> result = transactionService.getAllTransactionsByUser(3);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(50L, result.get(0).getAmount());
    }

	/*
	 * Testing - add transaction is successful or not
	 */
	@Test
	void createTransactionTest() {
		User user = new User();
		user.setId(1);
		user.setEmail("abc@gmail.com");
		user.setPassword("dsffdsfsdf");
		user.setName("John");

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setAmount(100L);
		transactionDto.setType("credit");
		transactionDto.setDescription("Salary");
		transactionDto.setDate("2025/10/10");

		Transaction transaction = new Transaction();
		transaction.setAmount(100L);
		transaction.setType("credit");
		transaction.setDescription("Salary");
		transaction.setDate("2025/10/10");

		Transaction savedTransaction = this.mapper.map(transactionDto, Transaction.class);
		savedTransaction.setUser(user);

		Mockito.when(userRepo.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(transactionRepo.save(any(Transaction.class))).thenReturn(savedTransaction);
		Mockito.doReturn(transaction).when(modelMapper).map(transactionDto, Transaction.class);
		Mockito.doReturn(transactionDto).when(modelMapper).map(savedTransaction, TransactionDto.class);

		TransactionDto transactionResultDto = transactionService.createTransaction(transactionDto, 1);

		Assertions.assertEquals(transactionDto, transactionResultDto);
		Assertions.assertEquals(transactionDto.getAmount(), transactionResultDto.getAmount());
		Assertions.assertEquals(transactionDto.getType(), transactionResultDto.getType());
		Assertions.assertEquals(transactionDto.getDescription(), transactionResultDto.getDescription());
	}
	
	
}
