package com.tracker.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.backend.entities.Document;
import com.tracker.backend.entities.User;

public interface DocumentRepo extends JpaRepository<Document, Integer> {
	Optional<Document> findByUser(User user);
}
