package com.tracker.backend.services;

import java.util.List;

import com.tracker.backend.payloads.TransactionDto;
import com.tracker.backend.payloads.TransactionResponse;

public interface TransactionService {
	// Create
	TransactionDto createTransaction(TransactionDto transactionDto, Integer userId);
	
	// Update
	TransactionDto updateTransaction(TransactionDto transactionDto, Integer transactionId);
	
	// Delete
	void deleteTransaction(Integer transactionId);
	
	// Get
	TransactionDto getTransaction(Integer transactionId);
	
	// Get All
	TransactionResponse getAllTransactions(Integer pageNumber, Integer pageSize, String sortBy);
	
	//Get All by User
	List<TransactionDto> getAllTransactionsByUser(Integer userId);
}
