package com.tracker.backend.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name="Transactions")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer entryId;

	@Column(name = "transactionDate", nullable = false)
	@Temporal(TemporalType.DATE)
	private String date;

	@Column(length = 200, nullable = false)
	private String description;

	@Column(nullable = false)
	private Long amount;

	@Column(name="typeOfTransaction",nullable = false)
	private String type;
	
	@ManyToOne
	private User user;
}
