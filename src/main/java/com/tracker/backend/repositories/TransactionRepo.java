package com.tracker.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.backend.entities.Transaction;
import com.tracker.backend.entities.User;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByUser(User user);
}
