package com.tracker.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.backend.config.AppConstants;
import com.tracker.backend.payloads.ApiResponse;
import com.tracker.backend.payloads.TransactionDto;
import com.tracker.backend.payloads.TransactionResponse;
import com.tracker.backend.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	// Create
	@PostMapping("/user/{userId}")
	public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionDto transactionDto,
			@PathVariable(name = "userId") Integer userId) {
		TransactionDto createdTransaction = this.transactionService.createTransaction(transactionDto, userId);
		return new ResponseEntity<TransactionDto>(createdTransaction, HttpStatus.CREATED);
	}

	// Update
	@PutMapping("/{transactionId}")
	public ResponseEntity<TransactionDto> updateTransaction(@Valid @RequestBody TransactionDto transactionDto,
			@PathVariable(name = "transactionId") Integer transactionId) {
		TransactionDto updatedTransaction = this.transactionService.updateTransaction(transactionDto, transactionId);
		return new ResponseEntity<TransactionDto>(updatedTransaction, HttpStatus.CREATED);
	}

	// Delete
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<ApiResponse> deleteTransaction(@PathVariable(name = "transactionId") Integer transactionId) {
		this.transactionService.deleteTransaction(transactionId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Transaction Deleted Succesfully", true), HttpStatus.OK);
	}

	// Get
	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionDto> getTransaction(@PathVariable(name = "transactionId") Integer TransactionId) {
		TransactionDto transaction = this.transactionService.getTransaction(TransactionId);
		return new ResponseEntity<TransactionDto>(transaction, HttpStatus.OK);
	}

	// Get All
	@GetMapping("/")
	public ResponseEntity<TransactionResponse> getAllTransaction(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue= AppConstants.SORT_BY, required = false) String sortBy
			) {
		TransactionResponse transactionResponse = this.transactionService.getAllTransactions(pageNumber, pageSize, sortBy);
		return new ResponseEntity<TransactionResponse>(transactionResponse, HttpStatus.OK);
	}

	// Get All transactions by UserId
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TransactionDto>> getAllTransactionByUser(@PathVariable(name = "userId") Integer userId) {
		List<TransactionDto> transactionByUser = this.transactionService.getAllTransactionsByUser(userId);
		return new ResponseEntity<List<TransactionDto>>(transactionByUser, HttpStatus.OK);
	}
}
