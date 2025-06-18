package com.tracker.backend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tracker.backend.entities.Transaction;
import com.tracker.backend.entities.User;
import com.tracker.backend.exceptions.ResourceNotFoundException;
import com.tracker.backend.payloads.TransactionDto;
import com.tracker.backend.payloads.TransactionResponse;
import com.tracker.backend.repositories.TransactionRepo;
import com.tracker.backend.repositories.UserRepo;
import com.tracker.backend.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TransactionDto createTransaction(TransactionDto transactionDto, Integer userId) {
		User user= this.getUserById(userId);
		Transaction transaction = this.modelMapper.map(transactionDto, Transaction.class);
		transaction.setUser(user);
		Transaction addedTransaction = transactionRepo.save(transaction);
		return this.modelMapper.map(addedTransaction, TransactionDto.class);
	}

	@Override
	public TransactionDto updateTransaction(TransactionDto transactionDto, Integer transactionId) {
		Transaction transaction = this.transactionRepo.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction", "transaction ID", transactionId));
		transaction.setAmount(transactionDto.getAmount());
		transaction.setDescription(transactionDto.getDescription());
		transaction.setType(transactionDto.getType());
		transaction.setDate(transactionDto.getDate());
		Transaction updatedTransaction = this.transactionRepo.save(transaction);
		return this.modelMapper.map(updatedTransaction, TransactionDto.class);
	}

	@Override
	public void deleteTransaction(Integer transactionId) {
		Transaction transaction = this.transactionRepo.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction", "transaction ID", transactionId));
		this.transactionRepo.delete(transaction);
	}

	@Override
	public TransactionDto getTransaction(Integer transactionId) {
		Transaction transaction = this.transactionRepo.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction", "transaction ID", transactionId));
		return this.modelMapper.map(transaction, TransactionDto.class);
	}

	@Override
	public TransactionResponse getAllTransactions(Integer pageNumber, Integer pageSize,String sortBy) {
		Pageable pageable= PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Transaction> pageTransaction= this.transactionRepo.findAll(pageable);
		List<Transaction> allTransactions= pageTransaction.getContent();
		List<TransactionDto> allTransactionDto= allTransactions.stream()
				.map(transaction -> this.modelMapper.map(transaction, TransactionDto.class))
				.collect(Collectors.toList());
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setContent(allTransactionDto);
		transactionResponse.setPageNumber(pageTransaction.getNumber());
		transactionResponse.setPageSize(pageTransaction.getSize());
		transactionResponse.setTotalElements(pageTransaction.getTotalElements());
		transactionResponse.setTotalPages(pageTransaction.getTotalPages());
		transactionResponse.setLastPage(pageTransaction.isLast());
		
		return transactionResponse;
	}

	@Override
	public List<TransactionDto> getAllTransactionsByUser(Integer userId) {
		User user= this.getUserById(userId);
		List<TransactionDto> transactionByUser = this.transactionRepo.findByUser(user).stream()
				.map((transaction) -> this.modelMapper.map(transaction, TransactionDto.class))
				.collect(Collectors.toList());
		return transactionByUser;
	}
	
	private User getUserById(Integer userId) {
		return this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user ID ", userId));
	}
}
